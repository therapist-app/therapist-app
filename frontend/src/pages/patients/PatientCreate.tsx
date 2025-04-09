import React, { useState } from 'react'
import {
  Box,
  Button,
  TextField,
  Typography,
  Grid,
  MenuItem,
  Snackbar,
  Alert,
} from '@mui/material'
import { useNavigate } from 'react-router-dom'
import { useAppDispatch } from '../../utils/hooks'
import { registerPatient } from '../../store/patientSlice'
import { useTranslation } from 'react-i18next'
import Layout from '../../generalComponents/Layout'

const PatientCreate = () => {
  const [name, setName] = useState('')
  const [age, setAge] = useState('')
  const [sex, setSex] = useState('')
  const [maritalStatus, setMaritalStatus] = useState('')
  const [religion, setReligion] = useState('')
  const [education, setEducation] = useState('')
  const [occupation, setOccupation] = useState('')
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
          name,
          age: Number(age),
          gender: sex,
          maritalStatus,
          religion,
          education,
          occupation,
          income,
          address,
          dateOfAdmission,
          mainComplaints,
          historyOfIllness,
          treatmentHistory,
          pastHistory,
          familyHistory,
          personalHistory,
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
        <Typography variant="h4" gutterBottom>
          {t('patient_create.new_patient')}
        </Typography>
        <Grid container spacing={2}>
          <Grid item xs={12} sm={6}>
            <TextField fullWidth label={t('patient_create.patient_name')} value={name} onChange={(e) => setName(e.target.value)} />
          </Grid>
          <Grid item xs={12} sm={6}>
            <TextField fullWidth type="number" label={t('patient_create.patient_age')} value={age} onChange={(e) => setAge(e.target.value)} />
          </Grid>
          <Grid item xs={12} sm={6}>
            <TextField select fullWidth label={t('patient_create.patient_gender')} value={sex} onChange={(e) => setSex(e.target.value)}>
              <MenuItem value="male">{t('patient_create.male')}</MenuItem>
              <MenuItem value="female">{t('patient_create.female')}</MenuItem>
              <MenuItem value="other">{t('patient_create.other')}</MenuItem>
            </TextField>
          </Grid>
          <Grid item xs={12} sm={6}>
            <TextField fullWidth label={t('patient_create.patient_marital_status')} value={maritalStatus} onChange={(e) => setMaritalStatus(e.target.value)} />
          </Grid>
          <Grid item xs={12} sm={6}>
            <TextField fullWidth label={t('patient_create.patient_religion')} value={religion} onChange={(e) => setReligion(e.target.value)} />
          </Grid>
          <Grid item xs={12} sm={6}>
            <TextField fullWidth label={t('patient_create.patient_education')} value={education} onChange={(e) => setEducation(e.target.value)} />
          </Grid>
          <Grid item xs={12} sm={6}>
            <TextField fullWidth label={t('patient_create.patient_occupation')} value={occupation} onChange={(e) => setOccupation(e.target.value)} />
          </Grid>
          <Grid item xs={12} sm={6}>
            <TextField fullWidth label={t('patient_create.patient_income')} value={income} onChange={(e) => setIncome(e.target.value)} />
          </Grid>
          <Grid item xs={12}>
            <TextField fullWidth label={t('patient_create.patient_address')} value={address} onChange={(e) => setAddress(e.target.value)} />
          </Grid>
          <Grid item xs={12}>
            <TextField
              fullWidth
              type="date"
              label={t('patient_create.patient_date_of_admission')}
              InputLabelProps={{ shrink: true }}
              value={dateOfAdmission}
              onChange={(e) => setDateOfAdmission(e.target.value)}
            />
          </Grid>
        </Grid>

        <Box mt={4} display="flex" justifyContent="flex-end">
          <Button onClick={() => navigate('/patients')} sx={{ mr: 2 }}>
            {t('patient_create.cancel')}
          </Button>
          <Button variant="contained" onClick={handleSubmit} disabled={!name || !age}>
            {t('patient_create.register')}
          </Button>
        </Box>

        <Snackbar open={snackbarOpen} autoHideDuration={6000} onClose={() => setSnackbarOpen(false)}>
          <Alert severity={snackbarSeverity}>{snackbarMessage}</Alert>
        </Snackbar>
      </Box>

      {/* SECTION 2: Main Complaints */}
      <Box mt={4}>
        <Typography variant="h6">2. {t('patient_create.main_complaints')}</Typography>
        <TextField
          fullWidth
          multiline
          rows={4}
          label={t('patient_create.main_complaints')}
          value={mainComplaints}
          onChange={(e) => setMainComplaints(e.target.value)}
          sx={{ mt: 1 }}
        />
      </Box>


      {/* SECTION 3: History of Present Illness */}
      <Box mt={4}>
        <Typography variant="h6">3. {t('patient_create.history_present_illness')}</Typography>

        {/* a) General Info */}
        <Typography variant="subtitle1" sx={{ mt: 2 }}>a) {t('patient_create.hpi_general_info')}</Typography>
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
              <MenuItem value="abrupt">{t('patient_create.hpi_onset_abrupt')}</MenuItem>
              <MenuItem value="acute">{t('patient_create.hpi_onset_acute')}</MenuItem>
              <MenuItem value="subacute">{t('patient_create.hpi_onset_subacute')}</MenuItem>
              <MenuItem value="insidious">{t('patient_create.hpi_onset_insidious')}</MenuItem>
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
              <MenuItem value="continuous">{t('patient_create.hpi_course_continuous')}</MenuItem>
              <MenuItem value="episodic">{t('patient_create.hpi_course_episodic')}</MenuItem>
              <MenuItem value="fluctuating">{t('patient_create.hpi_course_fluctuating')}</MenuItem>
              <MenuItem value="deteriorating">{t('patient_create.hpi_course_deteriorating')}</MenuItem>
              <MenuItem value="improving">{t('patient_create.hpi_course_improving')}</MenuItem>
              <MenuItem value="unclear">{t('patient_create.hpi_course_unclear')}</MenuItem>
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

        {/* b) Symptom Timeline */}
        <Typography variant="subtitle1" sx={{ mt: 3 }}>b) {t('patient_create.hpi_symptom_timeline')}</Typography>
        <TextField
          fullWidth
          multiline
          rows={6}
          value={hpiTimeline}
          onChange={(e) => setHpiTimeline(e.target.value)}
          label={t('patient_create.hpi_symptom_timeline')}
          sx={{ mt: 1 }}
        />
      </Box>


      {/* SECTION 4: Treatment History */}
      <Box mt={4}>
        <Typography variant="h6">4. {t('patient_create.treatment_history')}</Typography>

        <Typography variant="subtitle1" sx={{ mt: 2 }}>a) {t('patient_create.treatment_past')}</Typography>
        <TextField
          fullWidth
          multiline
          rows={4}
          value={treatmentPast}
          onChange={(e) => setTreatmentPast(e.target.value)}
          label={t('patient_create.treatment_past')}
          sx={{ mt: 1 }}
        />

        <Typography variant="subtitle1" sx={{ mt: 3 }}>b) {t('patient_create.treatment_current')}</Typography>
        <TextField
          fullWidth
          multiline
          rows={4}
          value={treatmentCurrent}
          onChange={(e) => setTreatmentCurrent(e.target.value)}
          label={t('patient_create.treatment_current')}
          sx={{ mt: 1 }}
        />
      </Box>

      {/* SECTION 5: Past History */}
      <Box mt={4}>
        <Typography variant="h6">5. {t('patient_create.past_history')}</Typography>

        <Typography variant="subtitle1" sx={{ mt: 2 }}>a) {t('patient_create.past_medical')}</Typography>
        <TextField
          fullWidth
          multiline
          rows={4}
          value={pastMedical}
          onChange={(e) => setPastMedical(e.target.value)}
          label={t('patient_create.past_medical')}
          sx={{ mt: 1 }}
        />

        <Typography variant="subtitle1" sx={{ mt: 3 }}>b) {t('patient_create.past_psych')}</Typography>
        <TextField
          fullWidth
          multiline
          rows={4}
          value={pastPsych}
          onChange={(e) => setPastPsych(e.target.value)}
          label={t('patient_create.past_psych')}
          sx={{ mt: 1 }}
        />
      </Box>

      {/* SECTION 6: Family History */}
      <Box mt={4}>
        <Typography variant="h6">6. {t('patient_create.family_history')}</Typography>

        <Typography variant="subtitle1" sx={{ mt: 2 }}>a) {t('patient_create.family_illness')}</Typography>
        <TextField
          fullWidth
          multiline
          rows={4}
          value={familyIllness}
          onChange={(e) => setFamilyIllness(e.target.value)}
          label={t('patient_create.family_illness')}
          sx={{ mt: 1 }}
        />

        <Typography variant="subtitle1" sx={{ mt: 3 }}>b) {t('patient_create.family_social')}</Typography>
        <TextField
          fullWidth
          multiline
          rows={4}
          value={familySocial}
          onChange={(e) => setFamilySocial(e.target.value)}
          label={t('patient_create.family_social')}
          sx={{ mt: 1 }}
        />
      </Box>

      {/* SECTION 7: Personal History */}
      <Box mt={4}>
        <Typography variant="h6">7. {t('patient_create.personal_history')}</Typography>

        {[['a', personalPerinatal, setPersonalPerinatal, 'personal_perinatal'],
          ['b', personalChildhood, setPersonalChildhood, 'personal_childhood'],
          ['c', personalEducation, setPersonalEducation, 'personal_education'],
          ['d', personalPlay, setPersonalPlay, 'personal_play'],
          ['e', personalAdolescence, setPersonalAdolescence, 'personal_adolescence'],
          ['f', personalPuberty, setPersonalPuberty, 'personal_puberty'],
          ['g', personalObstetric, setPersonalObstetric, 'personal_obstetric'],
          ['h', personalOccupational, setPersonalOccupational, 'personal_occupational'],
          ['i', personalMarital, setPersonalMarital, 'personal_marital'],
          ['j', personalPremorbid, setPersonalPremorbid, 'personal_premorbid']].map(
            ([label, value, setter, key]) => (
              <Box key={key as string} mt={3}>
                <Typography variant="subtitle1">{label}) {t(`patient_create.${key}`)}</Typography>
                <TextField
                  fullWidth
                  multiline
                  rows={4}
                  value={value as string}
                  onChange={(e) => (setter as React.Dispatch<React.SetStateAction<string>>)(e.target.value)}
                  label={t(`patient_create.${key}`)}
                  sx={{ mt: 1 }}
                />
              </Box>
            )
          )}
      </Box>
    </Layout>
  )
}

export default PatientCreate
