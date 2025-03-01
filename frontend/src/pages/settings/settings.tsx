import React from 'react'
import { useTranslation } from 'react-i18next'
import {
  FormControl,
  InputLabel,
  MenuItem,
  Select,
  Typography,
  SelectChangeEvent,
} from '@mui/material'

const Settings = () => {
  const { t, i18n } = useTranslation()

  const changeLanguage = (event: SelectChangeEvent) => {
    const selectedLanguage = event.target.value
    i18n.changeLanguage(selectedLanguage).then(() => {}) // Change the language
  }

  return (
    <div style={{ padding: '20px' }}>
      <Typography variant='h4' gutterBottom>
        {t('settings.title')}
      </Typography>
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
        </Select>
      </FormControl>
    </div>
  )
}

export default Settings
