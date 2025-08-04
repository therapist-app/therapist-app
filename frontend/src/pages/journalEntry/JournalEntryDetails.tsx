import { Typography } from '@mui/material'
import { ReactElement, useEffect } from 'react'
import { useTranslation } from 'react-i18next'
import { useSelector } from 'react-redux'
import { useParams } from 'react-router-dom'

import Layout from '../../generalComponents/Layout'
import { useNotify } from '../../hooks/useNotify'
import { getOneJournalEntry } from '../../store/journalEntrySlice'
import { RootState } from '../../store/store'
import { formatDateNicely } from '../../utils/dateUtil'
import { useAppDispatch } from '../../utils/hooks'

const JournalEntryDetails = (): ReactElement => {
  const { patientId, journalEntryId } = useParams()
  const { t } = useTranslation()
  const dispatch = useAppDispatch()
  const { notifyError } = useNotify()

  const selectedJournalEntry = useSelector(
    (state: RootState) => state.journalEntry.selectedJournalEntry
  )

  useEffect(() => {
    const loadEntry = async (): Promise<void> => {
      try {
        await dispatch(
          getOneJournalEntry({ journalEntryId: journalEntryId ?? '', patientId: patientId ?? '' })
        ).unwrap()
      } catch (err) {
        notifyError(typeof err === 'string' ? err : 'An unknown error occurred')
      }
    }
    loadEntry()
  }, [dispatch, patientId, journalEntryId, notifyError])

  return (
    <Layout>
      <div style={{ display: 'flex', flexDirection: 'column', gap: '20px' }}>
        <Typography variant='h2'>
          {t('journalEntry.title')}: {selectedJournalEntry?.title}
        </Typography>
        <Typography>
          <strong>{t('journalEntry.date')}: </strong>
          {formatDateNicely(selectedJournalEntry?.createdAt)}
        </Typography>
        <Typography>
          <strong>{t('journalEntry.tags')}: </strong>
          {Array.from(selectedJournalEntry?.tags ?? []).join(' | ')}
        </Typography>
        <Typography>
          <strong>{t('journalEntry.content')}: </strong>
          {selectedJournalEntry?.content}
        </Typography>
      </div>
    </Layout>
  )
}

export default JournalEntryDetails
