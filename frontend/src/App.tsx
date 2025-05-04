import { BrowserRouter as Router, Routes, Route } from 'react-router-dom'
import Dashboard from './pages/dashboard/Dashboard'
import Register from './pages/register/Register'
import Login from './pages/login/Login'
import NotFound from './pages/notFound/NotFound'
import PatientsOverview from './pages/patients/PatientOverview'

import ChatBotTemplateEdit from './pages/chatBotTemplate/ChatBotTemplateEdit'

import PatientDetail from './pages/patients/PatientDetail'
import Settings from './pages/settings/settings.tsx'
import PatientCreate from './pages/patients/PatientCreate.tsx'
import TherapySessionOverview from './pages/therapy-sessions/TherapySessionOverview.tsx'
import TherapySessionCreate from './pages/therapy-sessions/TherapySessionCreate.tsx'
import TherapySessionDetail from './pages/therapy-sessions/TherapySessionDetail.tsx'
import GAD7test from './pages/tests/GAD7test'
import { PAGES, ROUTES } from './utils/routes.ts'

const App = () => {
  const appContainerStyle = {
    display: 'grid',
    gridTemplateRows: 'auto 1fr auto',
    minHeight: '100vh',
  }

  const contentStyle = {
    padding: '20px',
  }

  const RootComponent = () => {
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

            <Route
              path={ROUTES[PAGES.THERAPY_SESSIONS_OVERVIEW_PAGE]}
              element={<TherapySessionOverview />}
            />
            <Route
              path={ROUTES[PAGES.THERAPY_SESSIONS_CREATE_PAGE]}
              element={<TherapySessionCreate />}
            />
            <Route
              path={ROUTES[PAGES.THERAPY_SESSIONS_DETAILS_PAGE]}
              element={<TherapySessionDetail />}
            />
            <Route path={ROUTES[PAGES.GAD7_TEST_PAGE]} element={<GAD7test />} />

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
