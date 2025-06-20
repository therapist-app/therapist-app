import 'i18next'

import { defaultNS } from '../i18n'
import Resources from './resources'

declare module 'i18next' {
  interface CustomTypeOptions {
    defaultNS: typeof defaultNS
    resources: Resources // Use the auto-generated types
  }
}
