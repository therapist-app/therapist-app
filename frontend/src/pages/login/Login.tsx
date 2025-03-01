import { Typography } from '@mui/material'
import { useDispatch, useSelector } from 'react-redux'
import { RootState } from '../../store/store'
import { TherapistOutputDTO } from '../../dto/output/TherapistOutputDTO'
import { clearLoggedInTherapist, setLoggedInTherapist } from '../../store/therapistSlice'

const Login = () => {
  const dispatch = useDispatch()
  const loggedInTherapist = useSelector((state: RootState) => state.therapist.loggedInTherapist)

  const handleLogin = () => {
    const therapist: TherapistOutputDTO = {
      id: '123',
      email: 'therapist@example.com',
      workspaceId: 'workspace-123',
    }
    dispatch(setLoggedInTherapist(therapist))
  }

  const handleLogout = () => {
    dispatch(clearLoggedInTherapist())
  }

  return (
    <div>
      <h1>Therapist Component</h1>
      {loggedInTherapist ? (
        <div>
          <p>Logged in as: {loggedInTherapist.email}</p>
          <button onClick={handleLogout}>Logout</button>
        </div>
      ) : (
        <button onClick={handleLogin}>Login</button>
      )}
    </div>
  )
}

export default Login
