import i18next from 'i18next'

import { TherapistChatbotInputDTOLanguageEnum } from '../api'

export function mapLanguageCodeToLanguage(
  languageCode: string
): TherapistChatbotInputDTOLanguageEnum {
  switch (languageCode) {
    case 'en':
      return TherapistChatbotInputDTOLanguageEnum.English
    case 'ua':
      return TherapistChatbotInputDTOLanguageEnum.Ukrainian
    case 'de':
      return TherapistChatbotInputDTOLanguageEnum.German
    default:
      return TherapistChatbotInputDTOLanguageEnum.English
  }
}

export function getCurrentLanguage(): TherapistChatbotInputDTOLanguageEnum {
  return mapLanguageCodeToLanguage(i18next.language)
}
