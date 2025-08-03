import { isAxiosError } from 'axios'

import { NotifyError } from '../hooks/useNotify'

interface ApiErrorResponse {
  message: string
  details?: Record<string, string>
}

function isApiErrorResponse(data: unknown): data is ApiErrorResponse {
  return (
    typeof data === 'object' &&
    data !== null &&
    'message' in data &&
    typeof (data as ApiErrorResponse).message === 'string'
  )
}

export const getErrorPayload = (error: unknown): string => {
  if (isAxiosError(error)) {
    if (error.response) {
      const data = error.response.data

      if (isApiErrorResponse(data)) {
        if (data.details && Object.keys(data.details).length > 0) {
          return Object.values(data.details)[0]
        }
        return data.message
      }

      return 'Received an invalid error format from the server.'
    } else {
      return 'Network error: Could not connect to the server.'
    }
  }
  return 'An unexpected error occurred.'
}

export const doErrorNotification = (notifyError: NotifyError, err: unknown): void => {
  notifyError(String(err))
}
