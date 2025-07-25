import {
  Box,
  Button,
  FormControl,
  InputLabel,
  MenuItem,
  Select,
  SelectChangeEvent,
  TextField,
  Typography,
} from '@mui/material'
import { ReactElement, useState } from 'react'
import { useTranslation } from 'react-i18next'
import { useNavigate } from 'react-router-dom'

import Layout from '../../generalComponents/Layout'
import { useNotify } from '../../hooks/useNotify'
import { logoutTherapist, updateTherapist } from '../../store/therapistSlice'
import { commonButtonStyles } from '../../styles/buttonStyles'
import { useAppDispatch } from '../../utils/hooks'
import { getPathFromPage, PAGES } from '../../utils/routes'

const Settings = (): ReactElement => {
  const dispatch = useAppDispatch()
  const { t, i18n } = useTranslation()
  const navigate = useNavigate()
  const { notifyError, notifySuccess } = useNotify()

  const [newPassword, setNewPassword] = useState('')
  const [confirmPassword, setConfirmPassword] = useState('')

  const changeLanguage = (event: SelectChangeEvent): void => {
    const selectedLanguage = event.target.value
    i18n
      .changeLanguage(selectedLanguage)
      .catch((err) => notifyError(typeof err === 'string' ? err : 'An unknown error occurred'))
  }

  const handlePasswordChange = async (event: React.FormEvent<HTMLFormElement>): Promise<void> => {
    event.preventDefault()

    if (!newPassword || !confirmPassword) {
      notifyError(t('settings.fill_all_fields') || 'Please fill all fields.')
      return
    }
    if (newPassword !== confirmPassword) {
      notifyError(t('settings.passwords_not_match') || 'Passwords do not match.')
      return
    }

    try {
      await dispatch(updateTherapist({ password: newPassword })).unwrap()
      await dispatch(logoutTherapist()).unwrap()
      notifySuccess(t('settings.password_changed_success') || 'Password changed.')
      navigate(getPathFromPage(PAGES.LOGIN_PAGE))
    } catch (err) {
      notifyError(typeof err === 'string' ? err : 'An unknown error occurred')
    } finally {
      setNewPassword('')
      setConfirmPassword('')
    }
  }

  return (
    <Layout>
      <div style={{ padding: '20px', maxWidth: '600px', margin: 'auto' }}>
        <Typography variant='h4' gutterBottom>
          {t('settings.title')}
        </Typography>

        <Box sx={{ mb: 4, mt: 2 }}>
          <FormControl fullWidth>
            <InputLabel id='language-select-label'>{t('settings.language')}</InputLabel>
            <Select
              labelId='language-select-label'
              value={i18n.language}
              label={t('settings.language')}
              onChange={changeLanguage}
            >
              <MenuItem value='en'>English</MenuItem>
              <MenuItem value='ua'>Українська</MenuItem>
              <MenuItem value='de'>Deutsch</MenuItem>
            </Select>
          </FormControl>
        </Box>

        <Box component='form' onSubmit={handlePasswordChange} noValidate sx={{ mt: 1 }}>
          <Typography variant='h5' gutterBottom>
            {t('settings.changePasswordTitle')}
          </Typography>

          <TextField
            margin='normal'
            required
            fullWidth
            name='newPassword'
            label={t('settings.newPassword')}
            type='password'
            id='newPassword'
            autoComplete='new-password'
            value={newPassword}
            onChange={(e) => setNewPassword(e.target.value)}
          />
          <TextField
            margin='normal'
            required
            fullWidth
            name='confirmPassword'
            label={t('settings.confirmNewPassword')}
            type='password'
            id='confirmPassword'
            autoComplete='new-password'
            value={confirmPassword}
            onChange={(e) => setConfirmPassword(e.target.value)}
          />

          <Button type='submit' sx={{ ...commonButtonStyles, minWidth: '580px', mt: 3, mb: 2 }}>
            {t('settings.changePasswordButton')}
          </Button>
        </Box>
      </div>
    </Layout>
  )
}

export default Settings
