import React, { useState, useEffect } from 'react'
import { useNavigate } from 'react-router-dom'
import {
  Alert,
  Avatar,
  Box,
  Button,
  Checkbox,
  Dialog,
  DialogActions,
  DialogContent,
  DialogContentText,
  DialogTitle,
  Pagination,
  Paper,
  Snackbar,
  Table,
  TableBody,
  TableCell,
  TableContainer,
  TableHead,
  TableRow,
  Tabs,
  Tab,
  TextField,
  Typography,
} from '@mui/material'
import AddIcon from '@mui/icons-material/Add'
import CheckIcon from '@mui/icons-material/Check'
import Layout from '../../generalComponents/Layout'
import { getPatientsForTherapist, registerPatient } from '../../services/patientService'
import { handleError } from '../../utils/handleError'
import { PatientOutputDTO } from '../../dto/output/PatientOutputDTO'
import { useTranslation } from 'react-i18next'
import api from '../../utils/api'

const PatientsOverview: React.FC = () => {
  const navigate = useNavigate()
  const { t } = useTranslation()

  const [patients, setPatients] = useState<PatientOutputDTO[]>([])
  const [openPatientDialog, setOpenPatientDialog] = useState(false)
  const [newPatientName, setNewPatientName] = useState('')
  const [snackbarOpen, setSnackbarOpen] = useState(false)
  const [snackbarMessage, setSnackbarMessage] = useState('')
  const [snackbarSeverity, setSnackbarSeverity] = useState<
    'info' | 'success' | 'error' | 'warning'
  >('info')

  const [selected, setSelected] = useState<string[]>([])

  const [page, setPage] = useState(1)
  const rowsPerPage = 5

  const [tabValue, setTabValue] = useState(0)
  const handleTabChange = (_event: React.SyntheticEvent, newValue: number) => {
    setTabValue(newValue)
  }

  const fetchTherapistPatients = async () => {
    try {
      const therapistId = sessionStorage.getItem('therapistId')
      if (!therapistId) {
        throw new Error('No therapistId found in session storage.')
      }
      const response = await getPatientsForTherapist()
      setPatients(response)
    } catch (error) {
      const errorMessage = handleError(error)
      setSnackbarMessage(errorMessage)
      setSnackbarSeverity('error')
      setSnackbarOpen(true)
    }
  }

  useEffect(() => {
    fetchTherapistPatients()
  }, [])

  const handleOpenPatientDialog = () => setOpenPatientDialog(true)
  const handleClosePatientDialog = () => {
    setOpenPatientDialog(false)
    setNewPatientName('')
  }

  const handleCreatePatient = async () => {
    try {
      const therapistId = sessionStorage.getItem('therapistId')
      if (!therapistId) {
        throw new Error('No therapistId found in session storage.')
      }
      await registerPatient(therapistId, { name: newPatientName })
      await fetchTherapistPatients()

      setSnackbarMessage(t('dashboard.patient_register_success'))
      setSnackbarSeverity('success')
      setSnackbarOpen(true)
      handleClosePatientDialog()
    } catch (error) {
      const errorMessage = handleError(error)
      setSnackbarMessage(errorMessage)
      setSnackbarSeverity('error')
      setSnackbarOpen(true)
    }
  }

  const handleSelectRow = (patientId: string) => {
    setSelected((prev) => {
      if (prev.includes(patientId)) {
        return prev.filter((id) => id !== patientId)
      }
      return [...prev, patientId]
    })
  }

  const handleDelete = async () => {
    try {
      await Promise.all(
        selected.map(async (id) => {
          await api.delete(`/patients/${id}`)
        })
      )
      setSnackbarMessage('Successfully deleted selected patient(s).')
      setSnackbarSeverity('success')
      setSnackbarOpen(true)

      setSelected([])
      fetchTherapistPatients()
    } catch (error) {
      const errorMessage = handleError(error)
      setSnackbarMessage(errorMessage)
      setSnackbarSeverity('error')
      setSnackbarOpen(true)
    }
  }

  const handlePatientClick = (patientId: string) => {
    navigate(`/patients/${patientId}`)
  }

  const startIndex = (page - 1) * rowsPerPage
  const endIndex = startIndex + rowsPerPage
  const paginatedPatients = patients.slice(startIndex, endIndex)

  const handleCloseSnackbar = (_event?: React.SyntheticEvent | Event, reason?: string) => {
    if (reason === 'clickaway') return
    setSnackbarOpen(false)
  }

  const allCount = patients.length || 5

  const tableCardStyles = {
    borderRadius: 3,
    boxShadow: '0 1px 2px rgba(16, 24, 40, 0.05)',
    border: '1px solid #E5E7EB',
  }

  const headerRowStyles = {
    mb: 2,
    display: 'flex',
    justifyContent: 'space-between',
    alignItems: 'center',
  }

  const gradientBackground = 'linear-gradient(45deg, #635BFF 30%, #7C4DFF 90%)'

  const addButtonStyles = {
    borderRadius: '8px',
    textTransform: 'none',
    fontWeight: 500,
    background: gradientBackground,
    color: '#fff',
    boxShadow: '0 3px 5px 2px rgba(99, 91, 255, .3)',
    '&:hover': {
      backgroundColor: '#7C4DFF',
      backgroundImage: 'none',
    },
  }

  const deleteButtonStyles = {
    textTransform: 'none',
    backgroundColor: '#F04438',
    color: '#fff',
    fontWeight: 500,
    borderRadius: '8px',
    '&:hover': {
      backgroundColor: '#D92C20',
    },
  }

  const tabsContainerStyles = {
    mb: 2,
    borderBottom: '1px solid #E5E7EB',
  }

  const tabStyles = {
    textTransform: 'none',
    minWidth: 'auto',
    fontSize: '0.95rem',
    fontWeight: 500,
    color: '#6B7280',
    px: 1.5,
    '&.Mui-selected': {
      color: '#111827',
    },
  }

  const tableHeadStyles = {
    backgroundColor: '#fff',
    '& .MuiTableCell-root': {
      color: '#6B7280',
      fontWeight: 600,
      fontSize: '0.875rem',
      borderBottom: 'none',
    },
  }

  const tableBodyStyles = {
    '& .MuiTableRow-root': {
      borderBottom: '1px solid #E5E7EB',
    },
    '& .MuiTableCell-root': {
      borderBottom: 'none',
      fontSize: '0.95rem',
      color: '#111827',
    },
  }

  const paginationRowStyles = {
    mt: 2,
    display: 'flex',
    justifyContent: 'space-between',
    alignItems: 'center',
  }

  const dialogPaperStyles = {
    width: '500px',
    borderRadius: '12px',
  }

  const registerButtonStyles = {
    borderRadius: '8px',
    textTransform: 'none',
    background: gradientBackground,
    color: '#fff',
    fontWeight: 500,
    boxShadow: '0 3px 5px 2px rgba(99, 91, 255, .3)',
    '&:hover': {
      backgroundColor: '#7C4DFF',
      backgroundImage: 'none',
    },
  }

  const cancelButtonStyles = {
    borderRadius: '8px',
    textTransform: 'none',
    backgroundColor: '#fff',
    color: '#635BFF',
    fontWeight: 500,
    '&:hover': {
      backgroundColor: '#F9FAFB',
    },
  }

  const customUncheckedIcon = (
    <Box
      sx={{
        width: 24,
        height: 24,
        borderRadius: '50%',
        border: '2px solid #635BFF',
      }}
    />
  )

  const customCheckedIcon = (
    <Box
      sx={{
        width: 24,
        height: 24,
        borderRadius: '50%',
        backgroundColor: '#635BFF',
        display: 'flex',
        alignItems: 'center',
        justifyContent: 'center',
      }}
    >
      <CheckIcon sx={{ color: '#fff', fontSize: '18px' }} />
    </Box>
  )

  const headerUncheckedIcon = (
    <Box
      sx={{
        width: 24,
        height: 24,
        borderRadius: '4px',
        border: '2px solid #635BFF',
      }}
    />
  )

  const headerIndeterminateIcon = (
    <Box
      sx={{
        width: 24,
        height: 24,
        borderRadius: '4px',
        backgroundColor: '#635BFF',
        display: 'flex',
        alignItems: 'center',
        justifyContent: 'center',
      }}
    >
      <Box
        sx={{
          width: 10,
          height: 2,
          backgroundColor: '#fff',
          borderRadius: 1,
        }}
      />
    </Box>
  )

  const headerCheckedIcon = (
    <Box
      sx={{
        width: 24,
        height: 24,
        borderRadius: '4px',
        backgroundColor: '#635BFF',
        display: 'flex',
        alignItems: 'center',
        justifyContent: 'center',
      }}
    >
      <CheckIcon sx={{ color: '#fff', fontSize: '18px' }} />
    </Box>
  )

  return (
    <Layout>
      <Box sx={headerRowStyles}>
        <Typography variant='h5' sx={{ fontWeight: 600 }}>
          Patients
        </Typography>
        <Box display='flex' alignItems='center'>
          {selected.length > 0 && (
            <>
              <Typography sx={{ mr: 2 }}>{selected.length} selected</Typography>
              <Button
                variant='contained'
                sx={{ ...deleteButtonStyles, mr: 2 }}
                onClick={handleDelete}
              >
                Delete
              </Button>
            </>
          )}
          <Button
            variant='contained'
            startIcon={<AddIcon />}
            onClick={handleOpenPatientDialog}
            sx={addButtonStyles}
          >
            Add
          </Button>
        </Box>
      </Box>

      <Box sx={tabsContainerStyles}>
        <Tabs
          value={tabValue}
          onChange={handleTabChange}
          TabIndicatorProps={{ style: { backgroundColor: '#635BFF' } }}
          sx={{ minHeight: '36px' }}
        >
          <Tab label={`All ${allCount}`} sx={tabStyles} />
        </Tabs>
      </Box>

      <TableContainer component={Paper} sx={tableCardStyles}>
        <Table>
          <TableHead sx={tableHeadStyles}>
            <TableRow>
              <TableCell padding='checkbox'>
                <Checkbox
                  checked={selected.length === patients.length && patients.length > 0}
                  indeterminate={selected.length > 0 && selected.length < patients.length}
                  onChange={(e) => {
                    if (e.target.checked) {
                      setSelected(patients.map((p) => p.id))
                    } else {
                      setSelected([])
                    }
                  }}
                  icon={headerUncheckedIcon}
                  checkedIcon={headerCheckedIcon}
                  indeterminateIcon={headerIndeterminateIcon}
                />
              </TableCell>
              <TableCell>Name</TableCell>
            </TableRow>
          </TableHead>

          <TableBody sx={tableBodyStyles}>
            {paginatedPatients.length > 0 ? (
              paginatedPatients.map((patient) => (
                <TableRow key={patient.id} hover>
                  <TableCell padding='checkbox'>
                    <Checkbox
                      checked={selected.includes(patient.id)}
                      onChange={() => handleSelectRow(patient.id)}
                      icon={customUncheckedIcon}
                      checkedIcon={customCheckedIcon}
                    />
                  </TableCell>

                  <TableCell sx={{ py: 2, verticalAlign: 'middle' }}>
                    <Box display='flex' alignItems='center'>
                      <Avatar
                        sx={{
                          width: 32,
                          height: 32,
                          mr: 1.5,
                          fontSize: '0.9rem',
                          backgroundColor: '#CBD5E1',
                          color: '#111827',
                        }}
                      >
                        {patient.name?.charAt(0).toUpperCase() || '?'}
                      </Avatar>
                      <Box>
                        <Typography
                          variant='subtitle2'
                          sx={{
                            fontWeight: 600,
                            cursor: 'pointer',
                            '&:hover': {
                              textDecoration: 'underline',
                            },
                          }}
                          onClick={() => handlePatientClick(patient.id)}
                        >
                          {patient.name || 'Unnamed Patient'}
                        </Typography>
                      </Box>
                    </Box>
                  </TableCell>
                </TableRow>
              ))
            ) : (
              <TableRow>
                <TableCell colSpan={2}>
                  <Typography variant='body2' align='center' sx={{ py: 2 }}>
                    {t('dashboard.no_patients_found')}
                  </Typography>
                </TableCell>
              </TableRow>
            )}
          </TableBody>
        </Table>
      </TableContainer>

      <Box sx={paginationRowStyles}>
        <Typography variant='body2' sx={{ color: '#6B7280' }}>
          Rows per page: {rowsPerPage}{' '}
          {`${startIndex + 1}-${Math.min(endIndex, patients.length)} of ${patients.length}`}
        </Typography>

        <Pagination
          count={Math.ceil(patients.length / rowsPerPage)}
          page={page}
          onChange={(_event, value) => setPage(value)}
          sx={{
            '& .MuiPaginationItem-root': {
              borderRadius: '20px',
              textTransform: 'none',
              fontWeight: 500,
              background: gradientBackground,
              color: '#fff',
              boxShadow: '0 3px 5px 2px rgba(99, 91, 255, .3)',
              margin: '0 4px',
              '&:hover': {
                backgroundColor: '#7C4DFF',
                backgroundImage: 'none',
              },
              '& .MuiPaginationItem-icon': {
                color: '#fff',
              },
            },
          }}
        />
      </Box>

      <Dialog
        open={openPatientDialog}
        onClose={handleClosePatientDialog}
        PaperProps={{ sx: dialogPaperStyles }}
      >
        <DialogTitle sx={{ fontWeight: 600 }}>New Customer</DialogTitle>
        <DialogContent sx={{ mt: 1 }}>
          <DialogContentText sx={{ mb: 1, color: '#6B7280' }}>
            Enter the name of your new customer:
          </DialogContentText>
          <TextField
            autoFocus
            margin='dense'
            id='patient-name'
            label='Customer Name'
            type='text'
            fullWidth
            variant='outlined'
            value={newPatientName}
            onChange={(e) => setNewPatientName(e.target.value)}
          />
        </DialogContent>
        <DialogActions sx={{ justifyContent: 'flex-end', pr: 2, pb: 2 }}>
          <Button onClick={handleClosePatientDialog} sx={cancelButtonStyles}>
            Cancel
          </Button>
          <Button
            onClick={handleCreatePatient}
            variant='contained'
            sx={registerButtonStyles}
            disabled={newPatientName.trim() === ''}
          >
            Register
          </Button>
        </DialogActions>
      </Dialog>

      <Snackbar
        open={snackbarOpen}
        autoHideDuration={6000}
        onClose={handleCloseSnackbar}
        anchorOrigin={{ vertical: 'bottom', horizontal: 'center' }}
      >
        <Alert onClose={handleCloseSnackbar} severity={snackbarSeverity} sx={{ width: '100%' }}>
          {snackbarMessage}
        </Alert>
      </Snackbar>
    </Layout>
  )
}

export default PatientsOverview
