import CheckIcon from '@mui/icons-material/Check'
import ClearIcon from '@mui/icons-material/Clear'
import DeleteIcon from '@mui/icons-material/Delete'
import EditIcon from '@mui/icons-material/Edit'
import { Button, Typography } from '@mui/material'

import { ExerciseTextOutputDTO } from '../api'
import { deleteExerciseText } from '../store/exerciseSlice'
import { useAppDispatch } from '../utils/hooks'

interface ShowExerciseTextProps {
  exercise: ExerciseTextOutputDTO
  refresh(): void
}

const ShowExerciseText: React.FC<ShowExerciseTextProps> = (props: ShowExerciseTextProps) => {
  const { exercise } = props
  const dispatch = useAppDispatch()

  const clickEdit = (): void => {
    //
  }

  const clickDelete = async (): Promise<void> => {
    await dispatch(deleteExerciseText(exercise.id ?? ''))

    props.refresh()
  }
  return (
    <div
      style={{
        display: 'flex',
        gap: '20px',
        flexDirection: 'column',
      }}
    >
      <div style={{ display: 'flex', gap: '5px', alignItems: 'center' }}>
        <Typography sx={{ fontWeight: 'bold' }}>{exercise.orderNumber}.</Typography>

        <Button sx={{ minWidth: '10px' }} onClick={clickEdit}>
          <EditIcon style={{ color: 'blue' }} />
        </Button>

        <Button sx={{ minWidth: '10px' }} onClick={clickDelete}>
          <DeleteIcon style={{ color: 'red' }} />
          <ClearIcon />
          <CheckIcon />
        </Button>
      </div>
      <Typography>{exercise.text}</Typography>
    </div>
  )
}

export default ShowExerciseText
