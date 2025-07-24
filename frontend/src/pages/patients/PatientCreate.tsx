import DeleteIcon from '@mui/icons-material/Delete'
import ExpandMoreIcon from '@mui/icons-material/ExpandMore'
import {
  Accordion,
  AccordionDetails,
  AccordionSummary,
  Box,
  Button,
  Grid,
  MenuItem,
  TextField,
  Typography,
} from '@mui/material'
import IconButton from '@mui/material/IconButton'
import { AxiosError } from 'axios'
import React, { ReactElement, useEffect, useState } from 'react'
import { useTranslation } from 'react-i18next'
import { useNavigate } from 'react-router-dom'
import { v4 as uuidv4 } from 'uuid'

import Layout from '../../generalComponents/Layout'
import { showError } from '../../store/errorSlice'
import { registerPatient } from '../../store/patientSlice'
import { getCurrentlyLoggedInTherapist } from '../../store/therapistSlice'
import {
  cancelButtonStyles,
  commonButtonStyles,
  disabledButtonStyles,
} from '../../styles/buttonStyles.ts'
import { handleError } from '../../utils/handleError.ts'
import { useAppDispatch } from '../../utils/hooks'
import { getPathFromPage, PAGES } from '../../utils/routes.ts'
import { AlertColor } from '@mui/material'

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

type CreatePatientDTO = {
  name: string
  gender: string
  age: number
  phoneNumber: string
  email: string
  address: string
  maritalStatus: string
  religion: string
  education: string
  occupation: string
  income: string
  dateOfAdmission: string
  complaints: Complaint[]
  treatmentPast: string
  treatmentCurrent: string
  pastMedical: string
  pastPsych: string
  familyIllness: string
  familySocial: string
  personalPerinatal: string
  personalChildhood: string
  personalEducation: string
  personalPlay: string
  personalAdolescence: string
  personalPuberty: string
  personalObstetric: string
  personalOccupational: string
  personalMarital: string
  personalPremorbid: string
}

const PatientCreate = (): ReactElement => {
  const dispatch = useAppDispatch()
  const { t } = useTranslation()
  const navigate = useNavigate()

  const [name, setName] = useState('')
  const [age, setAge] = useState('')
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

  const [refreshTherapistCounter, setRefreshTherapistCounter] = useState(0)

  const showMessage = (message: string, severity: AlertColor = 'error') => {
    dispatch(showError({ message, severity }))
  }

  const handleChange = (index: number, field: keyof Complaint, value: string): void => {
    const updated = [...complaints]
    updated[index] = {
      ...updated[index],
      [field]: value,
    }
    setComplaints(updated)
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

  const handleRemoveComplaint = (id: string): void => {
    setComplaints((prev) => prev.filter((c) => c.id !== id))
  }

  useEffect(() => {
    const fetchTherapist = async (): Promise<void> => {
      try {
        await dispatch(getCurrentlyLoggedInTherapist()).unwrap()
      } catch (error) {
        const msg = handleError(error as AxiosError)
        showMessage(msg, 'error')
      }
    }
    fetchTherapist()
  }, [dispatch, refreshTherapistCounter])

  const handleSubmit = async (): Promise<void> => {
    try {
      const resultAction = await dispatch(
        registerPatient({
          name: name,
          gender: sex,
          age: Number(age),
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
        } as CreatePatientDTO)
      )

      if (registerPatient.fulfilled.match(resultAction)) {
        const newPatient = resultAction.payload
        showMessage(t('patient_create.patient_register_success'), 'success')
        setRefreshTherapistCounter((prev) => prev + 1)
        navigate(getPathFromPage(PAGES.PATIENTS_DETAILS_PAGE, { patientId: newPatient.id! }))
      } else {
        throw new Error('Patient registration failed')
      }
    } catch (error) {
      const errorMessage = handleError(error as AxiosError)
      showMessage(errorMessage, 'error')
    }
  }

  return (
    <Layout>
      <Box sx={{ maxWidth: 800, mx: 'auto' }}>
        <Grid container spacing={2}>
          <Grid item xs={12} sm={6}>
            <TextField
              fullWidth
              required
              label={t('patient_create.patient_name')}
              value={name}
              onChange={(e) => setName(e.target.value)}
              autoComplete='off'
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
            />
          </Grid>
          <Grid item xs={12} sm={6}>
            <TextField
              fullWidth
              type='number'
              label={t('patient_create.patient_age')}
              value={age}
              onChange={(e) => setAge(e.target.value)}
              inputProps={{ min: 0 }}
            />
          </Grid>
          <Grid item xs={12} sm={6}>
            <TextField
              select
              fullWidth
              label={t('patient_create.patient_gender')}
              value={sex}
              onChange={(e) => setSex(e.target.value)}
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
            />
          </Grid>
          <Grid item xs={12} sm={6}>
            <TextField
              fullWidth
              label={t('patient_create.patient_religion')}
              value={religion}
              onChange={(e) => setReligion(e.target.value)}
            />
          </Grid>
          <Grid item xs={12} sm={6}>
            <TextField
              fullWidth
              label={t('patient_create.patient_education')}
              value={education}
              onChange={(e) => setEducation(e.target.value)}
            />
          </Grid>
          <Grid item xs={12} sm={6}>
            <TextField
              fullWidth
              label={t('patient_create.patient_occupation')}
              value={occupation}
              onChange={(e) => setOccupation(e.target.value)}
            />
          </Grid>
          <Grid item xs={12} sm={6}>
            <TextField
              fullWidth
              label={t('patient_create.patient_income')}
              value={income}
              onChange={(e) => setIncome(e.target.value)}
              type='number'
            />
          </Grid>
          <Grid item xs={12} sm={6}>
            <TextField
              fullWidth
              label={t('patient_create.patient_phone')}
              value={phoneNumber}
              onChange={(e) => setPhoneNumber(e.target.value)}
              type='tel'
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
            />
          </Grid>
          <Grid item xs={12}>
            <TextField
              fullWidth
              type='date'
              label={t('patient_create.patient_date_of_admission')}
              InputLabelProps={{ shrink: true }}
              value={dateOfAdmission}
              onChange={(e) => setDateOfAdmission(e.target.value)}
            />
          </Grid>
        </Grid>

        <Box mt={4} display='flex' justifyContent='flex-end'>
          <Button
            onClick={() => navigate(getPathFromPage(PAGES.HOME_PAGE))}
            sx={{ ...cancelButtonStyles, mr: 2 }}
          >
            {t('patient_create.cancel')}
          </Button>
          <Button
            variant='contained'
            onClick={handleSubmit}
            disabled={!name || !email}
            sx={!name || !email ? disabledButtonStyles : commonButtonStyles}
          >
            {t('patient_create.register')}
          </Button>
        </Box>
      </Box>

      <Box mt={6}>
        {complaints.map((complaint, index) => (
          <Accordion key={complaint.id} defaultExpanded>
            <AccordionSummary
              expandIcon={<ExpandMoreIcon />}
              aria-controls={`complaint-${index}-content`}
              id={`complaint-${index}-header`}
              sx={{ display: 'flex', justifyContent: 'space-between', alignItems: 'center' }}
            >
              <Box sx={{ display: 'flex', alignItems: 'center', gap: 2, flexGrow: 1 }}>
                <Typography variant='h6'>
                  {`${t('patient_create.complaint')} ${index + 1}`}
                </Typography>
              </Box>

              {complaints.length > 1 && (
                <IconButton
                  edge='end'
                  aria-label='delete'
                  onClick={(e) => {
                    e.stopPropagation()
                    handleRemoveComplaint(complaint.id)
                  }}
                >
                  <DeleteIcon color='error' />
                </IconButton>
              )}
            </AccordionSummary>
            <AccordionDetails>
              <TextField
                fullWidth
                label={t('patient_create.main_complaints')}
                value={complaint.mainComplaint}
                onChange={(e) => handleChange(index, 'mainComplaint', e.target.value)}
                sx={{ mb: 2 }}
              />
              <Grid container spacing={2}>
                <Grid item xs={12} sm={6}>
                  <TextField
                    fullWidth
                    label={t('patient_create.hpi_duration')}
                    value={complaint.duration}
                    onChange={(e) => handleChange(index, 'duration', e.target.value)}
                    placeholder='e.g 6 months'
                  />
                </Grid>
                <Grid item xs={12} sm={6}>
                  <TextField
                    select
                    fullWidth
                    label={t('patient_create.hpi_onset')}
                    value={complaint.onset}
                    onChange={(e) => handleChange(index, 'onset', e.target.value)}
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
                    placeholder='e.g., continuous, episodic'
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
                    placeholder='e.g., recent trauma, grief'
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
                    />
                    <TextField
                      fullWidth
                      label={t('patient_create.hpi_negative_history')}
                      value={complaint.negativeHistory}
                      onChange={(e) => handleChange(index, 'negativeHistory', e.target.value)}
                      placeholder={t('patient_create.hpi_negative_history_placeholder')}
                      multiline
                      rows={2}
                    />
                  </AccordionDetails>
                </Accordion>
              </Box>
            </AccordionDetails>
          </Accordion>
        ))}
        <Button
          variant='contained'
          onClick={handleAddComplaint}
          sx={commonButtonStyles}
          style={{ minWidth: '220px' }}
        >
          {t('patient_create.add_another_complaint')}
        </Button>
      </Box>

      <Box mt={4}>
        <Accordion>
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
            />
          </AccordionDetails>
        </Accordion>
      </Box>

      <Box mt={4}>
        <Accordion>
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
            />
          </AccordionDetails>
        </Accordion>
      </Box>

      <Box mt={4}>
        <Accordion>
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
            />
          </AccordionDetails>
        </Accordion>
      </Box>

      <Box mt={4}>
        <Accordion>
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
                />
              </Box>
            ))}
          </AccordionDetails>
        </Accordion>
      </Box>
    </Layout>
  )
}
export default PatientCreate
