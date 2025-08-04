import ExpandMoreIcon from '@mui/icons-material/ExpandMore'
import {
  Accordion,
  AccordionDetails,
  AccordionSummary,
  Alert,
  Box,
  Button,
  Grid,
  MenuItem,
  Snackbar,
  TextField,
  Typography,
} from '@mui/material'
import { DatePicker } from '@mui/x-date-pickers'
import { AdapterDateFns } from '@mui/x-date-pickers/AdapterDateFns'
import { LocalizationProvider } from '@mui/x-date-pickers/LocalizationProvider'
import { AxiosError } from 'axios'
import { format, parseISO } from 'date-fns'
import React, { JSX, useEffect, useState } from 'react'
import { useTranslation } from 'react-i18next'
import { useSelector } from 'react-redux'
import { useNavigate, useParams } from 'react-router-dom'
import { v4 as uuidv4 } from 'uuid'

import Layout from '../../generalComponents/Layout'
import { updatePatient } from '../../store/patientSlice'
import { RootState } from '../../store/store'
import { commonButtonStyles } from '../../styles/buttonStyles'
import { getCurrentLocale } from '../../utils/dateUtil'
import { handleError } from '../../utils/handleError'
import { useAppDispatch } from '../../utils/hooks'
import { getPathFromPage, PAGES } from '../../utils/routes'

type Complaint = {
  id: string
  mainComplaint: string
  duration: string
  onset: string
  course: string
  precipitatingFactors: string
  aggravatingRelieving: string
  timeline: string
  disturbances: string
  suicidalIdeation: string
  negativeHistory: string
}

const PatientDetailsUpdate = (): JSX.Element => {
  const { patientId } = useParams()
  const { t } = useTranslation()
  const dispatch = useAppDispatch()
  const navigate = useNavigate()
  const currentLocale = getCurrentLocale()

  const patient = useSelector((state: RootState) =>
    state.patient.allPatientsOfTherapist.find((p) => p.id === patientId?.toString())
  )

  const [isEditing, setIsEditing] = useState(false)

  // Form state
  const [name, setName] = useState('')
  const [age, setAge] = useState<number | string>('')
  const [sex, setSex] = useState('')
  const [maritalStatus, setMaritalStatus] = useState('')
  const [religion, setReligion] = useState('')
  const [education, setEducation] = useState('')
  const [occupation, setOccupation] = useState('')
  const [income, setIncome] = useState('')
  const [phoneNumber, setPhoneNumber] = useState('')
  const [email, setEmail] = useState('')
  const [address, setAddress] = useState('')
  const [dateOfAdmission, setDateOfAdmission] = useState('')
  const [complaints, setComplaints] = useState<Complaint[]>([
    {
      id: uuidv4(),
      mainComplaint: '',
      duration: '',
      onset: '',
      course: '',
      precipitatingFactors: '',
      aggravatingRelieving: '',
      timeline: '',
      disturbances: '',
      suicidalIdeation: '',
      negativeHistory: '',
    },
  ])

  const [treatmentPast, setTreatmentPast] = useState('')
  const [treatmentCurrent, setTreatmentCurrent] = useState('')
  const [pastMedical, setPastMedical] = useState('')
  const [pastPsych, setPastPsych] = useState('')
  const [familyIllness, setFamilyIllness] = useState('')
  const [familySocial, setFamilySocial] = useState('')
  const [personalPerinatal, setPersonalPerinatal] = useState('')
  const [personalChildhood, setPersonalChildhood] = useState('')
  const [personalEducation, setPersonalEducation] = useState('')
  const [personalPlay, setPersonalPlay] = useState('')
  const [personalAdolescence, setPersonalAdolescence] = useState('')
  const [personalPuberty, setPersonalPuberty] = useState('')
  const [personalObstetric, setPersonalObstetric] = useState('')
  const [personalOccupational, setPersonalOccupational] = useState('')
  const [personalMarital, setPersonalMarital] = useState('')
  const [personalPremorbid, setPersonalPremorbid] = useState('')

  const [snackbarOpen, setSnackbarOpen] = useState(false)
  const [snackbarMessage, setSnackbarMessage] = useState('')
  const [snackbarSeverity, setSnackbarSeverity] = useState<
    'info' | 'success' | 'error' | 'warning'
  >('info')

  useEffect(() => {
    if (patient) {
      setName(patient.name ?? '')
      setAge(patient.age ?? '')
      setSex(patient.gender ?? '')
      setMaritalStatus(patient.maritalStatus ?? '')
      setReligion(patient.religion ?? '')
      setEducation(patient.education ?? '')
      setOccupation(patient.occupation ?? '')
      setIncome(patient.income ?? '')
      setPhoneNumber(patient.phoneNumber ?? '')
      setEmail(patient.email ?? '')
      setAddress(patient.address ?? '')
      setDateOfAdmission(patient.dateOfAdmission ?? '')

      setComplaints(
        (patient.complaints ?? []).map((complaint) => ({
          id: complaint.id ?? uuidv4(), // fallback if id is undefined
          mainComplaint: complaint.mainComplaint ?? '',
          duration: complaint.duration ?? '',
          onset: complaint.onset ?? '',
          course: complaint.course ?? '',
          precipitatingFactors: complaint.precipitatingFactors ?? '',
          aggravatingRelieving: complaint.aggravatingRelieving ?? '',
          timeline: complaint.timeline ?? '',
          disturbances: complaint.disturbances ?? '',
          suicidalIdeation: complaint.suicidalIdeation ?? '',
          negativeHistory: complaint.negativeHistory ?? '',
        }))
      )
      setTreatmentPast(patient.treatmentPast ?? '')
      setTreatmentCurrent(patient.treatmentCurrent ?? '')
      setPastMedical(patient.pastMedical ?? '')
      setPastPsych(patient.pastPsych ?? '')
      setFamilyIllness(patient.familyIllness ?? '')
      setFamilySocial(patient.familySocial ?? '')
      setPersonalPerinatal(patient.personalPerinatal ?? '')
      setPersonalChildhood(patient.personalChildhood ?? '')
      setPersonalEducation(patient.personalEducation ?? '')
      setPersonalPlay(patient.personalPlay ?? '')
      setPersonalAdolescence(patient.personalAdolescence ?? '')
      setPersonalPuberty(patient.personalPuberty ?? '')
      setPersonalObstetric(patient.personalObstetric ?? '')
      setPersonalOccupational(patient.personalOccupational ?? '')
      setPersonalMarital(patient.personalMarital ?? '')
      setPersonalPremorbid(patient.personalPremorbid ?? '')
    }
  }, [patient])

  const handleUpdateSubmit = async (): Promise<void> => {
    try {
      const submittedAge = age !== undefined ? Number(age) : undefined

      await dispatch(
        updatePatient({
          patientId: patientId ?? '', // ensure it's not undefined
          updatePatientDetailDTO: {
            id: patientId ?? '',
            name: name,
            gender: sex,
            age: submittedAge,
            phoneNumber: phoneNumber,
            email: email,
            address: address,
            maritalStatus: maritalStatus,
            religion: religion,
            education: education,
            occupation: occupation,
            income: income,
            dateOfAdmission: dateOfAdmission,
            complaints: complaints,
            treatmentPast: treatmentPast,
            treatmentCurrent: treatmentCurrent,
            pastMedical: pastMedical,
            pastPsych: pastPsych,
            familyIllness: familyIllness,
            familySocial: familySocial,
            personalPerinatal: personalPerinatal,
            personalChildhood: personalChildhood,
            personalEducation: personalEducation,
            personalPlay: personalPlay,
            personalAdolescence: personalAdolescence,
            personalPuberty: personalPuberty,
            personalObstetric: personalObstetric,
            personalOccupational: personalOccupational,
            personalMarital: personalMarital,
            personalPremorbid: personalPremorbid,
          },
        })
      )

      setSnackbarMessage(t('patient_detail.update_success'))
      setSnackbarSeverity('success')
      setSnackbarOpen(true)
      setIsEditing(false)
      navigate(getPathFromPage(PAGES.PATIENTS_DETAILS_PAGE, { patientId: patientId! }), {
        replace: true,
      })
    } catch (error) {
      const errorMessage = await handleError(error as AxiosError)
      setSnackbarMessage(errorMessage)
      setSnackbarSeverity('error')
      setSnackbarOpen(true)
    }
  }

  const handleChange = (index: number, field: keyof Complaint, value: string): void => {
    const updated = [...complaints]
    updated[index] = {
      ...updated[index],
      [field]: value,
    }
    setComplaints(updated)
  }

  const dateValue = dateOfAdmission ? parseISO(dateOfAdmission) : null
  const handleDateChange = (newValue: Date | null): void => {
    const formattedDate = newValue ? format(newValue, 'yyyy-MM-dd') : ''
    setDateOfAdmission(formattedDate)
  }

  const handleAddComplaint = (): void => {
    setComplaints([
      ...complaints,
      {
        id: uuidv4(),
        mainComplaint: '',
        duration: '',
        onset: '',
        course: '',
        precipitatingFactors: '',
        aggravatingRelieving: '',
        timeline: '',
        disturbances: '',
        suicidalIdeation: '',
        negativeHistory: '',
      },
    ])
  }

  return (
    <Layout>
      <Box sx={{ maxWidth: 800, mx: 'auto' }}>
        <Box display='flex' justifyContent='flex-end' mb={2}>
          {!isEditing ? (
            <Button
              variant='contained'
              onClick={() => setIsEditing(true)}
              sx={{ ...commonButtonStyles }}
            >
              {t('patient_detail.edit')}
            </Button>
          ) : (
            <Button
              variant='contained'
              color='success'
              onClick={handleUpdateSubmit}
              sx={{ ...commonButtonStyles }}
            >
              {t('patient_detail.save_changes')}
            </Button>
          )}
        </Box>

        <Grid container spacing={2}>
          <Grid item xs={12} sm={6}>
            <TextField
              fullWidth
              required
              label={t('patient_create.patient_name')}
              value={name}
              onChange={(e) => setName(e.target.value)}
              disabled={!isEditing}
            />
          </Grid>
          <Grid item xs={12} sm={6}>
            <TextField
              fullWidth
              required
              label={t('patient_create.patient_email')}
              value={email}
              onChange={(e) => setEmail(e.target.value)}
              type='email'
              error={!!email && !/^\S+@\S+\.\S+$/.test(email)}
              helperText={
                email && !/^\S+@\S+\.\S+$/.test(email) ? t('patient_create.invalid_email') : ''
              }
              disabled={!isEditing}
            />
          </Grid>
          <Grid item xs={12} sm={6}>
            <TextField
              fullWidth
              type='number'
              label={t('patient_create.patient_age')}
              value={age === '' ? '' : age}
              onChange={(e) => {
                const inputValue = e.target.value
                setAge(inputValue === '' ? '' : inputValue)
              }}
              inputProps={{
                min: 1,
                inputMode: 'numeric',
                pattern: '[0-9]*',
              }}
              disabled={!isEditing}
            />
          </Grid>
          <Grid item xs={12} sm={6}>
            <TextField
              select
              fullWidth
              label={t('patient_create.patient_gender')}
              value={sex}
              onChange={(e) => setSex(e.target.value)}
              disabled={!isEditing}
            >
              <MenuItem value='male'>{t('patient_create.male')}</MenuItem>
              <MenuItem value='female'>{t('patient_create.female')}</MenuItem>
            </TextField>
          </Grid>
          <Grid item xs={12} sm={6}>
            <TextField
              fullWidth
              label={t('patient_create.patient_marital_status')}
              value={maritalStatus}
              onChange={(e) => setMaritalStatus(e.target.value)}
              disabled={!isEditing}
            />
          </Grid>
          <Grid item xs={12} sm={6}>
            <TextField
              fullWidth
              label={t('patient_create.patient_religion')}
              value={religion}
              onChange={(e) => setReligion(e.target.value)}
              disabled={!isEditing}
            />
          </Grid>
          <Grid item xs={12} sm={6}>
            <TextField
              fullWidth
              label={t('patient_create.patient_education')}
              value={education}
              onChange={(e) => setEducation(e.target.value)}
              disabled={!isEditing}
            />
          </Grid>
          <Grid item xs={12} sm={6}>
            <TextField
              fullWidth
              label={t('patient_create.patient_occupation')}
              value={occupation}
              onChange={(e) => setOccupation(e.target.value)}
              disabled={!isEditing}
            />
          </Grid>
          <Grid item xs={12} sm={6}>
            <TextField
              fullWidth
              label={t('patient_create.patient_income')}
              value={income}
              onChange={(e) => setIncome(e.target.value)}
              disabled={!isEditing}
            />
          </Grid>
          <Grid item xs={12} sm={6}>
            <TextField
              fullWidth
              label={t('patient_create.patient_phone')}
              value={phoneNumber}
              onChange={(e) => {
                const validatedValue = e.target.value.replace(/[^0-9+-\s]/g, '')
                setPhoneNumber(validatedValue)
              }}
              type='tel'
              inputProps={{
                pattern: '[0-9+-]*',
                inputMode: 'tel',
              }}
              disabled={!isEditing}
            />
          </Grid>
          <Grid item xs={12}>
            <TextField
              fullWidth
              label={t('patient_create.patient_address')}
              value={address}
              onChange={(e) => setAddress(e.target.value)}
              multiline
              rows={2}
              disabled={!isEditing}
            />
          </Grid>
          <Grid item xs={12}>
            <LocalizationProvider adapterLocale={currentLocale} dateAdapter={AdapterDateFns}>
              <DatePicker
                label={t('patient_create.patient_date_of_admission')}
                value={dateValue}
                onChange={handleDateChange}
                disabled={!isEditing}
                format='yyyy-MM-dd'
                slotProps={{
                  textField: {
                    fullWidth: true,
                    InputLabelProps: { shrink: true },
                  },
                }}
              />
            </LocalizationProvider>
          </Grid>
        </Grid>

        {/* SECTION 2: Complaints */}
        <Box mt={6}>
          {complaints.map((complaint, index) => (
            <Accordion key={complaint.id ?? `new-${index}`}>
              <AccordionSummary expandIcon={<ExpandMoreIcon />}>
                <Typography variant='h6'>
                  {`${t('patient_create.complaint')} ${index + 1}`}
                </Typography>
              </AccordionSummary>
              <AccordionDetails>
                <TextField
                  fullWidth
                  label={t('patient_create.main_complaints')}
                  value={complaint.mainComplaint}
                  onChange={(e) => handleChange(index, 'mainComplaint', e.target.value)}
                  sx={{ mb: 2 }}
                  disabled={!isEditing}
                />
                <Grid container spacing={2}>
                  <Grid item xs={12} sm={6}>
                    <TextField
                      fullWidth
                      label={t('patient_create.hpi_duration')}
                      value={complaint.duration}
                      onChange={(e) => handleChange(index, 'duration', e.target.value)}
                      placeholder='e.g. 6 months'
                      disabled={!isEditing}
                    />
                  </Grid>
                  <Grid item xs={12} sm={6}>
                    <TextField
                      select
                      fullWidth
                      label={t('patient_create.hpi_onset')}
                      value={complaint.onset}
                      onChange={(e) => handleChange(index, 'onset', e.target.value)}
                      disabled={!isEditing}
                    >
                      <MenuItem value='abrupt'>{t('patient_create.onset_abrupt')}</MenuItem>
                      <MenuItem value='acute'>{t('patient_create.onset_acute')}</MenuItem>
                      <MenuItem value='subacute'>{t('patient_create.onset_subacute')}</MenuItem>
                      <MenuItem value='insidious'>{t('patient_create.onset_insidious')}</MenuItem>
                    </TextField>
                  </Grid>
                  <Grid item xs={12} sm={6}>
                    <TextField
                      fullWidth
                      label={t('patient_create.hpi_course')}
                      value={complaint.course}
                      onChange={(e) => handleChange(index, 'course', e.target.value)}
                      placeholder='e.g. continuous, episodic'
                      disabled={!isEditing}
                    />
                  </Grid>
                  <Grid item xs={12}>
                    <TextField
                      fullWidth
                      label={t('patient_create.hpi_precipitating_factors')}
                      value={complaint.precipitatingFactors}
                      onChange={(e) => handleChange(index, 'precipitatingFactors', e.target.value)}
                      multiline
                      rows={2}
                      placeholder='e.g. recent trauma, grief'
                      disabled={!isEditing}
                    />
                  </Grid>
                  <Grid item xs={12}>
                    <TextField
                      fullWidth
                      label={t('patient_create.hpi_aggravating_relieving')}
                      value={complaint.aggravatingRelieving}
                      onChange={(e) => handleChange(index, 'aggravatingRelieving', e.target.value)}
                      multiline
                      rows={2}
                      placeholder={t('patient_create.hpi_aggravating_relieving_placeholder')}
                      disabled={!isEditing}
                    />
                  </Grid>
                  <Grid item xs={12}>
                    <TextField
                      fullWidth
                      label={t('patient_create.hpi_timeline')}
                      value={complaint.timeline}
                      onChange={(e) => handleChange(index, 'timeline', e.target.value)}
                      multiline
                      rows={3}
                      placeholder={t('patient_create.hpi_timeline_placeholder')}
                      disabled={!isEditing}
                    />
                  </Grid>
                </Grid>
                <Box mt={3}>
                  <Accordion>
                    <AccordionSummary expandIcon={<ExpandMoreIcon />}>
                      <Typography>{t('patient_create.hpi_advanced_details')}</Typography>
                    </AccordionSummary>
                    <AccordionDetails>
                      <TextField
                        fullWidth
                        label={t('patient_create.hpi_disturbances')}
                        value={complaint.disturbances}
                        onChange={(e) => handleChange(index, 'disturbances', e.target.value)}
                        placeholder={t('patient_create.hpi_disturbances_placeholder')}
                        multiline
                        rows={2}
                        sx={{ mb: 2 }}
                        disabled={!isEditing}
                      />
                      <TextField
                        fullWidth
                        label={t('patient_create.hpi_suicidal_ideation')}
                        value={complaint.suicidalIdeation}
                        onChange={(e) => handleChange(index, 'suicidalIdeation', e.target.value)}
                        placeholder={t('patient_create.yes_no_explanation')}
                        multiline
                        rows={2}
                        sx={{ mb: 2 }}
                        disabled={!isEditing}
                      />
                      <TextField
                        fullWidth
                        label={t('patient_create.hpi_negative_history')}
                        value={complaint.negativeHistory}
                        onChange={(e) => handleChange(index, 'negativeHistory', e.target.value)}
                        placeholder={t('patient_create.hpi_negative_history_placeholder')}
                        multiline
                        rows={2}
                        disabled={!isEditing}
                      />
                    </AccordionDetails>
                  </Accordion>
                </Box>
              </AccordionDetails>
            </Accordion>
          ))}
          {isEditing && (
            <Button
              variant='contained'
              onClick={handleAddComplaint}
              sx={{ ...commonButtonStyles, mt: 2, mb: 4 }}
              style={{ minWidth: '200px' }}
            >
              {t('patient_create.add_another_complaint')}
            </Button>
          )}
        </Box>

        {/* SECTION 3: Treatment History */}
        <Box mt={4}>
          <Accordion defaultExpanded>
            <AccordionSummary expandIcon={<ExpandMoreIcon />}>
              <Typography variant='h6'>{t('patient_create.treatment_history')}</Typography>
            </AccordionSummary>
            <AccordionDetails>
              <Typography variant='subtitle1' sx={{ mt: 2 }}>
                a) {t('patient_create.treatment_past')}
              </Typography>
              <TextField
                fullWidth
                multiline
                rows={4}
                value={treatmentPast}
                onChange={(e) => setTreatmentPast(e.target.value)}
                label={t('patient_create.treatment_past')}
                sx={{ mt: 1 }}
                disabled={!isEditing}
              />

              <Typography variant='subtitle1' sx={{ mt: 3 }}>
                b) {t('patient_create.treatment_current')}
              </Typography>
              <TextField
                fullWidth
                multiline
                rows={4}
                value={treatmentCurrent}
                onChange={(e) => setTreatmentCurrent(e.target.value)}
                label={t('patient_create.treatment_current')}
                sx={{ mt: 1 }}
                disabled={!isEditing}
              />
            </AccordionDetails>
          </Accordion>
        </Box>

        {/* SECTION 4: Past History */}
        <Box mt={4}>
          <Accordion defaultExpanded>
            <AccordionSummary expandIcon={<ExpandMoreIcon />}>
              <Typography variant='h6'>{t('patient_create.past_history')}</Typography>
            </AccordionSummary>
            <AccordionDetails>
              <Typography variant='subtitle1' sx={{ mt: 2 }}>
                a) {t('patient_create.past_medical')}
              </Typography>
              <TextField
                fullWidth
                multiline
                rows={4}
                value={pastMedical}
                onChange={(e) => setPastMedical(e.target.value)}
                label={t('patient_create.past_medical')}
                sx={{ mt: 1 }}
                disabled={!isEditing}
              />

              <Typography variant='subtitle1' sx={{ mt: 3 }}>
                b) {t('patient_create.past_psych')}
              </Typography>
              <TextField
                fullWidth
                multiline
                rows={4}
                value={pastPsych}
                onChange={(e) => setPastPsych(e.target.value)}
                label={t('patient_create.past_psych')}
                sx={{ mt: 1 }}
                disabled={!isEditing}
              />
            </AccordionDetails>
          </Accordion>
        </Box>

        {/* SECTION 5: Family History */}
        <Box mt={4}>
          <Accordion defaultExpanded>
            <AccordionSummary expandIcon={<ExpandMoreIcon />}>
              <Typography variant='h6'>{t('patient_create.family_history')}</Typography>
            </AccordionSummary>
            <AccordionDetails>
              <Typography variant='subtitle1' sx={{ mt: 2 }}>
                a) {t('patient_create.family_illness')}
              </Typography>
              <TextField
                fullWidth
                multiline
                rows={4}
                value={familyIllness}
                onChange={(e) => setFamilyIllness(e.target.value)}
                label={t('patient_create.family_illness')}
                sx={{ mt: 1 }}
                disabled={!isEditing}
              />

              <Typography variant='subtitle1' sx={{ mt: 3 }}>
                b) {t('patient_create.family_social')}
              </Typography>
              <TextField
                fullWidth
                multiline
                rows={4}
                value={familySocial}
                onChange={(e) => setFamilySocial(e.target.value)}
                label={t('patient_create.family_social')}
                sx={{ mt: 1 }}
                disabled={!isEditing}
              />
            </AccordionDetails>
          </Accordion>
        </Box>

        {/* SECTION 6: Personal History */}
        <Box mt={4}>
          <Accordion defaultExpanded>
            <AccordionSummary expandIcon={<ExpandMoreIcon />}>
              <Typography variant='h6'>{t('patient_create.personal_history')}</Typography>
            </AccordionSummary>
            <AccordionDetails>
              {[
                ['a', personalPerinatal, setPersonalPerinatal, 'personal_perinatal'],
                ['b', personalChildhood, setPersonalChildhood, 'personal_childhood'],
                ['c', personalEducation, setPersonalEducation, 'personal_education'],
                ['d', personalPlay, setPersonalPlay, 'personal_play'],
                ['e', personalAdolescence, setPersonalAdolescence, 'personal_adolescence'],
                ['f', personalPuberty, setPersonalPuberty, 'personal_puberty'],
                ['g', personalObstetric, setPersonalObstetric, 'personal_obstetric'],
                ['h', personalOccupational, setPersonalOccupational, 'personal_occupational'],
                ['i', personalMarital, setPersonalMarital, 'personal_marital'],
                ['j', personalPremorbid, setPersonalPremorbid, 'personal_premorbid'],
              ].map(([label, value, setter, key]) => (
                <Box key={key as string} mt={3}>
                  <Typography variant='subtitle1'>
                    {`${label}) ${t(`patient_create.${key}`)}`}
                  </Typography>
                  <TextField
                    fullWidth
                    multiline
                    rows={4}
                    value={value as string}
                    onChange={(e) =>
                      (setter as React.Dispatch<React.SetStateAction<string>>)(e.target.value)
                    }
                    label={t(`patient_create.${key}`)}
                    sx={{ mt: 1 }}
                    disabled={!isEditing}
                  />
                </Box>
              ))}
            </AccordionDetails>
          </Accordion>
        </Box>
        <Snackbar
          open={snackbarOpen}
          autoHideDuration={6000}
          onClose={() => setSnackbarOpen(false)}
        >
          <Alert severity={snackbarSeverity}>{snackbarMessage}</Alert>
        </Snackbar>
      </Box>
    </Layout>
  )
}

export default PatientDetailsUpdate
