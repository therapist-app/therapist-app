import {
  Paper,
  Table,
  TableBody,
  TableCell,
  TableContainer,
  TableHead,
  TableRow,
  Typography,
} from '@mui/material'
import { ReactElement, useEffect } from 'react'
import { useTranslation } from 'react-i18next'
import { useSelector } from 'react-redux'
import { useNavigate, useParams } from 'react-router-dom'

import { listAllJournalEntries } from '../../store/journalEntrySlice'
import { RootState } from '../../store/store'
import { formatDateNicely } from '../../utils/dateUtil'
import { useAppDispatch } from '../../utils/hooks'
import { getPathFromPage, PAGES } from '../../utils/routes'

const JournalEntriesOverview = (): ReactElement => {
  const { patientId } = useParams()
  const { t } = useTranslation()
  const navigate = useNavigate()
  const dispatch = useAppDispatch()

  const allJournalEntries = useSelector((state: RootState) => state.journalEntry.allJournalEntries)

  const handleClickOnJournalEntry = (journalEntryId: string): void => {
    navigate(
      getPathFromPage(PAGES.JOURNAL_ENTRIES_DETAILS_PAGE, {
        patientId: patientId ?? '',
        journalEntryId: journalEntryId,
      })
    )
  }

  useEffect(() => {
    const loadEntries = async (): Promise<void> => {
      await dispatch(listAllJournalEntries(patientId ?? '')).unwrap()
    }
    loadEntries()
  }, [dispatch, patientId])

  return (
    <>
      <Typography variant='h2'>{t('journalEntry.overviewTitle')}</Typography>

      {allJournalEntries.length == 0 ? (
        <Typography variant='body1' sx={{ marginTop: 2 }}>
          {t('journalEntry.no_entries')}
        </Typography>
      ) : (
        <TableContainer component={Paper}>
          <Table aria-label='simple table' sx={{ tableLayout: 'fixed' }}>
            <TableHead>
              <TableRow>
                <TableCell>{t('journalEntry.title')}</TableCell>
                <TableCell>{t('journalEntry.date')}</TableCell>
              </TableRow>
            </TableHead>
            <TableBody>
              {allJournalEntries.map((journalEntry) => (
                <TableRow
                  onClick={() => handleClickOnJournalEntry(journalEntry.id ?? '')}
                  key={journalEntry.id}
                  sx={{
                    '&:last-child td, &:last-child th': { border: 0 },
                    cursor: 'pointer',
                  }}
                >
                  <TableCell component='th' scope='row'>
                    {journalEntry.title ? journalEntry.title : t('journalEntry.noTitle')}
                  </TableCell>
                  <TableCell>{formatDateNicely(journalEntry.createdAt)}</TableCell>
                </TableRow>
              ))}
            </TableBody>
          </Table>
        </TableContainer>
      )}
    </>
  )
}

export default JournalEntriesOverview
