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
