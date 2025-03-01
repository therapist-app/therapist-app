// src/i18n.ts
import i18next from 'i18next'
import { initReactI18next } from 'react-i18next'
import HttpApi from 'i18next-http-backend'

export const defaultNS = 'translation' // Default namespace

i18next
  .use(initReactI18next)
  .use(HttpApi)
  .init({
    debug: true,
    fallbackLng: 'en',
    defaultNS,
    backend: {
      loadPath: '/locales/{{lng}}.json', // Path to your translation files
    },
  })

export default i18next
