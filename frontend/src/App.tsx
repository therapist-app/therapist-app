import { BrowserRouter as Router, Routes, Route, useLocation } from 'react-router-dom'
import Header from './generalComponents/Header'
import Footer from './generalComponents/Footer'
import Dashboard from './pages/dashboard/Dashboard'
import Register from './pages/register/Register'
import Login from './pages/login/Login'
import NotFound from './pages/notFound/NotFound'
import PatientsOverview from './pages/patients/PatientOverview'
import PatientChatBotCreate from './pages/patients/chatBots/PatientChatBotCreate'
import PatientChatBotEdit from './pages/patients/chatBots/PatientChatBotEdit'
import ChatBotTemplateEdit from './pages/chatBotTemplate/ChatBotTemplateEdit'
import ChatBotTemplateCreate from './pages/chatBotTemplate/ChatBotTemplateCreate'
import PatientDetail from './pages/patients/PatientDetail'

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
    const location = useLocation()
    const queryParams = new URLSearchParams(location.search)
    const workspaceId = queryParams.get('workspaceId')
    return <Dashboard workspaceId={workspaceId} />
  }

  return (
    <Router>
      <div style={appContainerStyle}>
        <Header />
        <main style={contentStyle}>
          <Routes>
            <Route path='/' element={<RootComponent />} />
            <Route path='/register' element={<Register />} />
            <Route path='/login' element={<Login />} />
            <Route path='/patients' element={<PatientsOverview />} />
            <Route path='/patients/:patientId' element={<PatientDetail />} />
            <Route path='/patients/:patientId/chatBot/create' element={<PatientChatBotCreate />} />
            <Route
              path='/patients/:patientId/chatBot/:chatBotId'
              element={<PatientChatBotEdit />}
            />
            <Route path='/chatBotTemplate/create' element={<ChatBotTemplateCreate />} />
            <Route
              path='/?workspace_id=:workspaceId/?chatbot_template_id=:chatbotTemplateId'
              element={<ChatBotTemplateEdit />}
            />
            <Route path='*' element={<NotFound />} />
          </Routes>
        </main>
        <Footer />
      </div>
    </Router>
  )
}

export default App
