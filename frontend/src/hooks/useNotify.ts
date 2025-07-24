import { AlertColor } from '@mui/material'
import { AxiosError } from 'axios'
import { useCallback } from 'react'

import { showError } from '../store/errorSlice'
import { handleError } from '../utils/handleError'
import { useAppDispatch } from '../utils/hooks'

type Notify = (message: string, severity?: AlertColor) => void
type NotifyError = (error: unknown) => void

export const useNotify = () => {
  const dispatch = useAppDispatch()

  const notify: Notify = useCallback(
    (message: string, severity: AlertColor = 'error') => {
      dispatch(showError({ message, severity }))
    },
    [dispatch]
  )

  const notifyError: NotifyError = useCallback(
    (err: unknown) => {
      notify(handleError(err as AxiosError), 'error')
    },
    [notify]
  )

  const notifySuccess = useCallback(
    (message: string) => notify(message, 'success'),
    [notify]
  )
  const notifyInfo = useCallback(
    (message: string) => notify(message, 'info'),
    [notify]
  )
  const notifyWarning = useCallback(
    (message: string) => notify(message, 'warning'),
    [notify]
  )

  return { notify, notifyError, notifySuccess, notifyInfo, notifyWarning }
}
