// src/i18n.ts
import i18next from 'i18next'
import LanguageDetector from 'i18next-browser-languagedetector'
import HttpApi from 'i18next-http-backend'
import { initReactI18next } from 'react-i18next'

export const defaultNS = 'translation'

const supportedLngs = ['en', 'ua', 'de']

const loadPath = `${import.meta.env.BASE_URL}locales/{{lng}}.json`

i18next
  .use(initReactI18next)
  .use(HttpApi)
  .use(LanguageDetector)
  .init({
    supportedLngs: supportedLngs,
    fallbackLng: 'en',
    defaultNS: defaultNS,

    backend: {
      loadPath: loadPath,
    },
    detection: {
      order: ['localStorage'],
      caches: ['localStorage'],
    },
  })

export default i18next
