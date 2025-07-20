import { ReactElement } from 'react'
import { BrowserRouter as Router, Route, Routes } from 'react-router-dom'

import ScrollToTop from './generalComponents/ScrollToTop.tsx'
import ConversationSummary from './pages/chatbot/ConversationSummary.tsx'
import ChatBotTemplateEdit from './pages/chatBotTemplate/ChatBotTemplateEdit'
import CounselingPlanDetails from './pages/counselingPlan/CounselingPlanDetails.tsx'
import ExerciseCreate from './pages/exercises/ExerciseCreate.tsx'
import ExerciseDetail from './pages/exercises/ExerciseDetail.tsx'
import GAD7TestCreate from './pages/gad7Test/GAD7TestCreate.tsx'
import Home from './pages/home/Home.tsx'
import ClientInteractions from './pages/interactions/ClientInteractions.tsx'
import Login from './pages/login/Login'
import MeetingCreate from './pages/meetings/MeetingCreate.tsx'
import MeetingDetail from './pages/meetings/MeetingDetail.tsx'
import NotFound from './pages/notFound/NotFound'
import PatientCreate from './pages/patients/PatientCreate.tsx'
import PatientDetail from './pages/patients/PatientDetail'
import Register from './pages/register/Register'
import Settings from './pages/settings/settings.tsx'
import TherapistChatbot from './pages/therapistChatbot/TherapistChatbot.tsx'
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

  return (
    <Router>
      <div style={appContainerStyle}>
        <ScrollToTop />
        <main style={contentStyle}>
          <Routes>
            <Route path={ROUTES[PAGES.HOME_PAGE]} element={<Home />} />
            <Route path={ROUTES[PAGES.REGISTRATION_PAGE]} element={<Register />} />
            <Route path={ROUTES[PAGES.LOGIN_PAGE]} element={<Login />} />

            <Route path={ROUTES[PAGES.PATIENTS_CREATE_PAGE]} element={<PatientCreate />} />
            <Route path={ROUTES[PAGES.PATIENTS_DETAILS_PAGE]} element={<PatientDetail />} />

            <Route path={ROUTES[PAGES.CHATBOT_CREATE_PAGE]} element={<NotFound />} />
            <Route path={ROUTES[PAGES.CHATBOT_DETAILS_PAGE]} element={<NotFound />} />

            <Route path={ROUTES[PAGES.MEETINGS_CREATE_PAGE]} element={<MeetingCreate />} />
            <Route path={ROUTES[PAGES.MEETINGS_DETAILS_PAGE]} element={<MeetingDetail />} />

            <Route path={ROUTES[PAGES.GAD7_TEST_CREATE_PAGE]} element={<GAD7TestCreate />} />

            <Route path={ROUTES[PAGES.EXERCISES_CREATE_PAGE]} element={<ExerciseCreate />} />
            <Route path={ROUTES[PAGES.EXERCISES_DETAILS_PAGE]} element={<ExerciseDetail />} />

            <Route
              path={ROUTES[PAGES.CHATBOT_TEMPLATES_DETAILS_PAGE]}
              element={<ChatBotTemplateEdit />}
            />

            <Route
              path={ROUTES[PAGES.COUNSELING_PLAN_DETAILS_PAGE]}
              element={<CounselingPlanDetails />}
            />

            <Route
              path={ROUTES[PAGES.PATIENT_CONVERSATIONS_PAGE]}
              element={<ConversationSummary />}
            />

            <Route path={ROUTES[PAGES.CLIENT_INTERACTIONS_PAGE]} element={<ClientInteractions />} />

            <Route path={ROUTES[PAGES.THERAPIST_CHATBOT_PAGE]} element={<TherapistChatbot />} />
            <Route
              path={ROUTES[PAGES.THERAPIST_CHATBOT_PAGE_BY_PATIENT]}
              element={<TherapistChatbot />}
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
