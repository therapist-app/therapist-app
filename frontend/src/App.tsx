import { ReactElement } from 'react'
import { BrowserRouter as Router, Route, Routes } from 'react-router-dom'

import ChatBotTemplateEdit from './pages/chatBotTemplate/ChatBotTemplateEdit'
import Dashboard from './pages/dashboard/Dashboard'
import ExerciseCreate from './pages/exercises/ExerciseCreate.tsx'
import ExerciseDetail from './pages/exercises/ExerciseDetail.tsx'
import ExerciseOverview from './pages/exercises/ExerciseOverview.tsx'
import Login from './pages/login/Login'
import MeetingCreate from './pages/meetings/MeetingCreate.tsx'
import MeetingDetail from './pages/meetings/MeetingDetail.tsx'
import MeetingOverview from './pages/meetings/MeetingOverview.tsx'
import NotFound from './pages/notFound/NotFound'
import PatientCreate from './pages/patients/PatientCreate.tsx'
import PatientDetail from './pages/patients/PatientDetail'
import PatientsOverview from './pages/patients/PatientOverview'
import Register from './pages/register/Register'
import Settings from './pages/settings/settings.tsx'
import GAD7test from './pages/tests/GAD7test'
import { PAGES, ROUTES } from './utils/routes.ts'

const App = (): ReactElement => {
  const appContainerStyle = {
    display: 'grid',

    gridTemplateRows: 'auto 1fr auto',
    minHeight: '100vh',
    boxSizing: 'border-box' as const,
  }

  const contentStyle = {
    padding: '20px',
  }

  const RootComponent = (): ReactElement => {
    return <Dashboard />
  }

  return (
    <Router>
      <div style={appContainerStyle}>
        <main style={contentStyle}>
          <Routes>
            <Route path={ROUTES[PAGES.HOME_PAGE]} element={<RootComponent />} />
            <Route path={ROUTES[PAGES.REGISTRATION_PAGE]} element={<Register />} />
            <Route path={ROUTES[PAGES.LOGIN_PAGE]} element={<Login />} />

            <Route path={ROUTES[PAGES.PATIENTS_OVERVIEW_PAGE]} element={<PatientsOverview />} />
            <Route path={ROUTES[PAGES.PATIENTS_CREATE_PAGE]} element={<PatientCreate />} />
            <Route path={ROUTES[PAGES.PATIENTS_DETAILS_PAGE]} element={<PatientDetail />} />

            <Route path={ROUTES[PAGES.CHATBOT_OVERVIEW_PAGE]} element={<NotFound />} />

            <Route path={ROUTES[PAGES.MEETINGS_OVERVIEW_PAGE]} element={<MeetingOverview />} />
            <Route path={ROUTES[PAGES.MEETINGS_CREATE_PAGE]} element={<MeetingCreate />} />
            <Route path={ROUTES[PAGES.MEETINGS_DETAILS_PAGE]} element={<MeetingDetail />} />

            <Route path={ROUTES[PAGES.GAD7_TEST_PAGE]} element={<GAD7test />} />

            <Route path={ROUTES[PAGES.EXERCISES_CREATE_PAGE]} element={<ExerciseCreate />} />
            <Route path={ROUTES[PAGES.EXERCISES_DETAILS_PAGE]} element={<ExerciseDetail />} />
            <Route path={ROUTES[PAGES.EXERCISES_OVERVIEW_PAGE]} element={<ExerciseOverview />} />

            <Route path={ROUTES[PAGES.CHATBOT_TEMPLATES_OVERVIEW_PAGE]} element={<NotFound />} />

            <Route
              path={ROUTES[PAGES.CHATBOT_TEMPLATES_DETAILS_PAGE]}
              element={<ChatBotTemplateEdit />}
            />

            <Route path={ROUTES[PAGES.SETTINGS_PAGE]} element={<Settings />} />
            <Route path={ROUTES[PAGES.NOT_FOUND_PAGE]} element={<NotFound />} />
          </Routes>
        </main>
      </div>
    </Router>
  )
}

export default App
