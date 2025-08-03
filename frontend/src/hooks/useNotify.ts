import { AlertColor } from '@mui/material'
import { AxiosError } from 'axios'
import { useCallback } from 'react'

import { showError } from '../store/errorSlice'
import { handleError } from '../utils/handleError'
import { useAppDispatch } from '../utils/hooks'

type Notify = (message: string, severity?: AlertColor) => void
type NotifyError = (error: unknown) => void

interface UseNotifyReturn {
  notify: Notify
  notifyError: NotifyError
  notifySuccess: (message: string) => void
  notifyInfo: (message: string) => void
  notifyWarning: (message: string) => void
}

export const useNotify = (): UseNotifyReturn => {
  const dispatch = useAppDispatch()

  const notify: Notify = useCallback(
    (message: string, severity: AlertColor = 'error'): void => {
      dispatch(showError({ message: message, severity: severity }))
    },
    [dispatch]
  )

  const notifyError: NotifyError = useCallback(
    (err: unknown): void => {
      Promise.resolve(handleError(err as AxiosError))
        .then((msg) => notify(msg, 'error'))
        .catch(() => notify('An unknown error occurred', 'error'))
    },
    [notify]
  )

  const notifySuccess = useCallback((message: string) => notify(message, 'success'), [notify])
  const notifyInfo = useCallback((message: string) => notify(message, 'info'), [notify])
  const notifyWarning = useCallback((message: string) => notify(message, 'warning'), [notify])

  return {
    notify: notify,
    notifyError: notifyError,
    notifySuccess: notifySuccess,
    notifyInfo: notifyInfo,
    notifyWarning: notifyWarning,
  }
}
