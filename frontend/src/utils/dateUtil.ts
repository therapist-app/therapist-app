import { format } from 'date-fns'
import { de } from 'date-fns/locale'

export const formatDateNicely = (date: Date | null | undefined | string): string => {
  if (!date) {
    return ''
  }
  return format(new Date(date), 'dd.MM.yyyy HH:mm', {
    locale: de,
  })
}

export const getMinutesBetweenDates = (
  startDate: Date | undefined | string,
  endDate: Date | undefined | string
): number => {
  if (startDate === undefined || endDate === undefined) {
    return 0
  }
  return Math.floor((new Date(endDate).getTime() - new Date(startDate).getTime()) / 60000)
}

export const isNowBetweenDates = (
  startDate: Date | string | undefined,
  endDate: Date | string | undefined
): boolean => {
  if (!startDate || !endDate) {
    return false
  }
  startDate = new Date(startDate)
  endDate = new Date(endDate)
  const now = new Date()
  return (
    now.getTime() > startDate.getTime() &&
    now.getTime() < endDate.getTime() &&
    startDate.getTime() < endDate.getTime()
  )
}

export const isDateInThePast = (date: Date | string | undefined): boolean => {
  if (!date) {
    return true
  }
  date = new Date(date)
  return date.getTime() < new Date().getTime()
}
