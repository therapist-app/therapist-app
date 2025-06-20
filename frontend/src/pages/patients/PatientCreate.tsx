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
import React, { useState } from 'react'
import { useTranslation } from 'react-i18next'
import { useNavigate } from 'react-router-dom'

import Layout from '../../generalComponents/Layout'
import { registerPatient } from '../../store/patientSlice'
import { useAppDispatch } from '../../utils/hooks'

const PatientCreate: React.FC = () => {
  const [name, setName] = useState('')
  const [age, setAge] = useState('')
  const [sex, setSex] = useState('')
  const [maritalStatus, setMaritalStatus] = useState('')
  const [religion, setReligion] = useState('')
  const [education, setEducation] = useState('')
  const [occupation, setOccupation] = useState('')
  const [phoneNumber, setPhoneNumber] = useState('')
  const [email, setEmail] = useState('')
  const [income, setIncome] = useState('')
  const [address, setAddress] = useState('')
  const [dateOfAdmission, setDateOfAdmission] = useState('')
  const [mainComplaints, setMainComplaints] = useState('')
  const [historyOfIllness, setHistoryOfIllness] = useState('')
  const [treatmentHistory, setTreatmentHistory] = useState('')
  const [pastHistory, setPastHistory] = useState('')
  const [familyHistory, setFamilyHistory] = useState('')
  const [personalHistory, setPersonalHistory] = useState('')
  const [hpiGeneral, setHpiGeneral] = useState('')
  const [hpiDuration, setHpiDuration] = useState('')
  const [hpiOnset, setHpiOnset] = useState('')
  const [hpiCourse, setHpiCourse] = useState('')
  const [hpiPrecipitatingFactors, setHpiPrecipitatingFactors] = useState('')
  const [hpiAggravatingRelieving, setHpiAggravatingRelieving] = useState('')
  const [hpiTimeline, setHpiTimeline] = useState('')
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
  const [snackbarSeverity, setSnackbarSeverity] = useState<'success' | 'error'>('success')

  const dispatch = useAppDispatch()
  const { t } = useTranslation()
  const navigate = useNavigate()

  const handleSubmit = async () => {
    try {
      dispatch(
        registerPatient({
          name: name,
          age: Number(age),
          gender: sex,
          maritalStatus: maritalStatus,
          religion: religion,
          education: education,
          occupation: occupation,
          income: income,
          address: address,
          dateOfAdmission: dateOfAdmission,
          phoneNumber: phoneNumber,
          email: email,
          mainComplaints: mainComplaints,
          historyOfIllness: historyOfIllness,
          treatmentHistory: treatmentHistory,
          pastHistory: pastHistory,
          familyHistory: familyHistory,
          personalHistory: personalHistory,
        })
      )
      setSnackbarMessage(t('patient_create.patient_register_success'))
      setSnackbarSeverity('success')
      setSnackbarOpen(true)
      navigate('/patients')
    } catch (err) {
      setSnackbarMessage(t('patient_create.patient_register_failed'))
      setSnackbarSeverity('error')
      setSnackbarOpen(true)
    }
  }

  return (
    <Layout>
      <Box sx={{ p: 4, maxWidth: 800, mx: 'auto' }}>
        <Typography variant='h6' gutterBottom>
          1. {t('patient_create.create_patient')}
        </Typography>
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
              required
              label={t('patient_create.patient_gender')}
              value={sex}
              onChange={(e) => setSex(e.target.value)}
            >
              <MenuItem value='male'>{t('patient_create.male')}</MenuItem>
              <MenuItem value='female'>{t('patient_create.female')}</MenuItem>
              <MenuItem value='other'>{t('patient_create.other')}</MenuItem>
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
          <Grid item xs={12} sm={6}>
            <TextField
              fullWidth
              label={t('patient_create.patient_email')}
              value={email}
              onChange={(e) => setEmail(e.target.value)}
              type='email'
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
          <Button onClick={() => navigate('/patients')} sx={{ mr: 2 }}>
            {t('patient_create.cancel')}
          </Button>
          <Button variant='contained' onClick={handleSubmit} disabled={!name || !age}>
            {t('patient_create.register')}
          </Button>
        </Box>

        <Snackbar
          open={snackbarOpen}
          autoHideDuration={6000}
          onClose={() => setSnackbarOpen(false)}
        >
          <Alert severity={snackbarSeverity}>{snackbarMessage}</Alert>
        </Snackbar>
      </Box>

      {/* SECTION 2: Main Complaints */}
      <Box mt={6}>
        <Accordion defaultExpanded>
          <AccordionSummary
            expandIcon={<ExpandMoreIcon />}
            aria-controls='section2-content'
            id='section2-header'
          >
            <Typography variant='h6'>
              2. {t('patient_create.main_complaints')} &{' '}
              {t('patient_create.history_of_present_illness')}
            </Typography>
          </AccordionSummary>

          <AccordionDetails>
            {/* Main Complaints */}
            <TextField
              fullWidth
              multiline
              rows={4}
              label={t('patient_create.main_complaints')}
              value={mainComplaints}
              onChange={(e) => setMainComplaints(e.target.value)}
              sx={{ mb: 4 }}
            />

            {/* History of Present Illness - Basic Fields */}
            <Typography variant='subtitle1' sx={{ mb: 2 }}>
              {t('patient_create.history_of_present_illness')}
            </Typography>
            <Grid container spacing={2}>
              <Grid item xs={12} sm={6}>
                <TextField
                  fullWidth
                  label={t('patient_create.hpi_duration')}
                  value={hpiDuration}
                  onChange={(e) => setHpiDuration(e.target.value)}
                  placeholder='e.g., 6 months'
                />
              </Grid>
              <Grid item xs={12} sm={6}>
                <TextField
                  select
                  fullWidth
                  label={t('patient_create.hpi_onset')}
                  value={hpiOnset}
                  onChange={(e) => setHpiOnset(e.target.value)}
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
                  value={hpiCourse}
                  onChange={(e) => setHpiCourse(e.target.value)}
                  placeholder='e.g., continuous, episodic'
                />
              </Grid>
              <Grid item xs={12}>
                <TextField
                  fullWidth
                  label={t('patient_create.hpi_precipitating_factors')}
                  value={hpiPrecipitatingFactors}
                  onChange={(e) => setHpiPrecipitatingFactors(e.target.value)}
                  multiline
                  rows={2}
                  placeholder='e.g., recent trauma, grief'
                />
              </Grid>
              <Grid item xs={12}>
                <TextField
                  fullWidth
                  label={t('patient_create.hpi_aggravating_relieving')}
                  value={hpiAggravatingRelieving}
                  onChange={(e) => setHpiAggravatingRelieving(e.target.value)}
                  multiline
                  rows={2}
                  placeholder={t('patient_create.hpi_aggravating_relieving_placeholder')}
                />
              </Grid>
              <Grid item xs={12}>
                <TextField
                  fullWidth
                  label={t('patient_create.hpi_timeline')}
                  value={hpiTimeline}
                  onChange={(e) => setHpiTimeline(e.target.value)}
                  multiline
                  rows={3}
                  placeholder={t('patient_create.hpi_timeline_placeholder')}
                />
              </Grid>
            </Grid>

            {/* Advanced HPI Fields */}
            <Box mt={4}>
              <Accordion>
                <AccordionSummary
                  expandIcon={<ExpandMoreIcon />}
                  aria-controls='hpi-advanced-content'
                  id='hpi-advanced-header'
                >
                  <Typography variant='subtitle1'>
                    {t('patient_create.hpi_advanced_details')}
                  </Typography>
                </AccordionSummary>
                <AccordionDetails>
                  <Grid container spacing={2}>
                    <Grid item xs={12}>
                      <TextField
                        fullWidth
                        label={t('patient_create.hpi_disturbances')}
                        value={hpiGeneral}
                        onChange={(e) => setHpiGeneral(e.target.value)}
                        placeholder={t('patient_create.hpi_disturbances_placeholder')}
                        multiline
                        rows={2}
                      />
                    </Grid>

                    <Grid item xs={12}>
                      <TextField
                        fullWidth
                        label={t('patient_create.hpi_suicidal_ideation')}
                        placeholder={t('patient_create.yes_no_explanation')}
                        value={historyOfIllness}
                        onChange={(e) => setHistoryOfIllness(e.target.value)}
                        multiline
                        rows={2}
                      />
                    </Grid>

                    <Grid item xs={12}>
                      <TextField
                        fullWidth
                        label={t('patient_create.hpi_negative_history')}
                        value={hpiTimeline}
                        onChange={(e) => setHpiTimeline(e.target.value)}
                        placeholder={t('patient_create.hpi_negative_history_placeholder')}
                        multiline
                        rows={2}
                      />
                    </Grid>
                  </Grid>
                </AccordionDetails>
              </Accordion>
            </Box>
          </AccordionDetails>
        </Accordion>
      </Box>

      {/* SECTION 3: History of Present Illness */}
      <Box mt={4}>
        <Accordion>
          <AccordionSummary expandIcon={<ExpandMoreIcon />}>
            <Typography variant='h6'>3. {t('patient_create.history_present_illness')}</Typography>
          </AccordionSummary>
          <AccordionDetails>
            <Typography variant='subtitle1' sx={{ mt: 2 }}>
              a) {t('patient_create.hpi_general_info')}
            </Typography>
            <Grid container spacing={2}>
              <Grid item xs={12} sm={6}>
                <TextField
                  fullWidth
                  label={t('patient_create.hpi_duration')}
                  value={hpiDuration}
                  onChange={(e) => setHpiDuration(e.target.value)}
                />
              </Grid>
              <Grid item xs={12} sm={6}>
                <TextField
                  fullWidth
                  select
                  label={t('patient_create.hpi_onset')}
                  value={hpiOnset}
                  onChange={(e) => setHpiOnset(e.target.value)}
                >
                  <MenuItem value='abrupt'>{t('patient_create.hpi_onset_abrupt')}</MenuItem>
                  <MenuItem value='acute'>{t('patient_create.hpi_onset_acute')}</MenuItem>
                  <MenuItem value='subacute'>{t('patient_create.hpi_onset_subacute')}</MenuItem>
                  <MenuItem value='insidious'>{t('patient_create.hpi_onset_insidious')}</MenuItem>
                </TextField>
              </Grid>
              <Grid item xs={12} sm={6}>
                <TextField
                  fullWidth
                  select
                  label={t('patient_create.hpi_course')}
                  value={hpiCourse}
                  onChange={(e) => setHpiCourse(e.target.value)}
                >
                  <MenuItem value='continuous'>
                    {t('patient_create.hpi_course_continuous')}
                  </MenuItem>
                  <MenuItem value='episodic'>{t('patient_create.hpi_course_episodic')}</MenuItem>
                  <MenuItem value='fluctuating'>
                    {t('patient_create.hpi_course_fluctuating')}
                  </MenuItem>
                  <MenuItem value='deteriorating'>
                    {t('patient_create.hpi_course_deteriorating')}
                  </MenuItem>
                  <MenuItem value='improving'>{t('patient_create.hpi_course_improving')}</MenuItem>
                  <MenuItem value='unclear'>{t('patient_create.hpi_course_unclear')}</MenuItem>
                </TextField>
              </Grid>
              <Grid item xs={12}>
                <TextField
                  fullWidth
                  multiline
                  rows={2}
                  label={t('patient_create.hpi_precipitating_factors')}
                  value={hpiPrecipitatingFactors}
                  onChange={(e) => setHpiPrecipitatingFactors(e.target.value)}
                />
              </Grid>
              <Grid item xs={12}>
                <TextField
                  fullWidth
                  multiline
                  rows={2}
                  label={t('patient_create.hpi_aggravating_relieving')}
                  value={hpiAggravatingRelieving}
                  onChange={(e) => setHpiAggravatingRelieving(e.target.value)}
                />
              </Grid>
            </Grid>

            <Typography variant='subtitle1' sx={{ mt: 3 }}>
              b) {t('patient_create.hpi_symptom_timeline')}
            </Typography>
            <TextField
              fullWidth
              multiline
              rows={6}
              value={hpiTimeline}
              onChange={(e) => setHpiTimeline(e.target.value)}
              label={t('patient_create.hpi_symptom_timeline')}
              sx={{ mt: 1 }}
            />
          </AccordionDetails>
        </Accordion>
      </Box>

      {/* SECTION 4: Treatment History */}
      <Box mt={4}>
        <Accordion>
          <AccordionSummary expandIcon={<ExpandMoreIcon />}>
            <Typography variant='h6'>4. {t('patient_create.treatment_history')}</Typography>
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

      {/* SECTION 5: Past History */}
      <Box mt={4}>
        <Accordion>
          <AccordionSummary expandIcon={<ExpandMoreIcon />}>
            <Typography variant='h6'>5. {t('patient_create.past_history')}</Typography>
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

      {/* SECTION 6: Family History */}
      <Box mt={4}>
        <Accordion>
          <AccordionSummary expandIcon={<ExpandMoreIcon />}>
            <Typography variant='h6'>6. {t('patient_create.family_history')}</Typography>
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

      {/* SECTION 7: Personal History */}
      <Box mt={4}>
        <Accordion>
          <AccordionSummary expandIcon={<ExpandMoreIcon />}>
            <Typography variant='h6'>7. {t('patient_create.personal_history')}</Typography>
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
                  {label}) {t(`patient_create.${key}`)}
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
