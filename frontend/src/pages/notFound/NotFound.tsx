import { Typography } from '@mui/material'
import { ReactElement } from 'react'
import { useTranslation } from 'react-i18next'

const NotFound = (): ReactElement => {
  const { t } = useTranslation()

  return (
    <div>
      <Typography variant='h3'>{t('not_found.title')}</Typography>
      <Typography variant='body1'>{t('not_found.message')}</Typography>
    </div>
  )
}

export default NotFound
