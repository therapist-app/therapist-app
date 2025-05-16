import CheckIcon from '@mui/icons-material/Check'
import ClearIcon from '@mui/icons-material/Clear'
import DeleteIcon from '@mui/icons-material/Delete'
import EditIcon from '@mui/icons-material/Edit'
import { Button, MenuItem, TextField, Typography } from '@mui/material'
import { useState } from 'react'

import { ExerciseTextOutputDTO, UpdateExerciseTextDTO } from '../api'
import { deleteExerciseText, updateExerciseText } from '../store/exerciseSlice'
import { useAppDispatch } from '../utils/hooks'

interface ShowExerciseTextProps {
  exercise: ExerciseTextOutputDTO
  numberOfExercises: number
  refresh(): void
}

const ShowExerciseText: React.FC<ShowExerciseTextProps> = (props: ShowExerciseTextProps) => {
  const { exercise } = props
  const dispatch = useAppDispatch()

  const originalFormData: UpdateExerciseTextDTO = {
    id: exercise.id ?? '',
    text: exercise.text,
    orderNumber: exercise.orderNumber,
  }

  const [formData, setFormData] = useState<UpdateExerciseTextDTO>({
    id: exercise.id ?? '',
    text: exercise.text,
    orderNumber: exercise.orderNumber,
  })

  const [isEditing, setIsEditing] = useState(false)

  const arrayOfNumbers: number[] = Array.from(
    { length: props.numberOfExercises },
    (value, index) => index + 1
  )

  const clickCancel = (event: React.MouseEvent<HTMLButtonElement>): void => {
    event.stopPropagation()
    setIsEditing(false)
    setFormData({ ...originalFormData })
  }

  const clickEdit = (): void => {
    setIsEditing(true)
  }

  const handleChange = (e: React.ChangeEvent<HTMLInputElement>): void => {
    setFormData({ ...formData, [e.target.name]: e.target.value })
  }

  const handleSubmit = async (): Promise<void> => {
    try {
      await dispatch(updateExerciseText(formData)).unwrap()
      setIsEditing(false)
      props.refresh()
    } catch (err) {
      console.error(err)
    }
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
      {isEditing === false ? (
        <>
          <div style={{ display: 'flex', gap: '5px', alignItems: 'center' }}>
            <Typography sx={{ fontWeight: 'bold' }}>{exercise.orderNumber}.</Typography>

            <Button sx={{ minWidth: '10px', marginLeft: '20px' }} onClick={clickEdit}>
              <EditIcon style={{ color: 'blue' }} />
            </Button>

            <Button sx={{ minWidth: '10px' }} onClick={clickDelete}>
              <DeleteIcon style={{ color: 'red' }} />
            </Button>
          </div>

          <Typography
            sx={{
              whiteSpace: 'pre-line',
            }}
          >
            {exercise.text}
          </Typography>
        </>
      ) : (
        <>
          <div style={{ display: 'flex', gap: '5px', alignItems: 'center' }}>
            <TextField
              select
              sx={{ fontWeight: 'bold', width: '75px' }}
              label='Order'
              name='orderNumber'
              value={formData.orderNumber}
              onChange={handleChange}
            >
              {arrayOfNumbers.map((option: number) => (
                <MenuItem key={option} value={option}>
                  {option}
                </MenuItem>
              ))}
            </TextField>

            <Button sx={{ minWidth: '10px', marginLeft: '20px' }} onClick={clickCancel}>
              <ClearIcon style={{ color: 'red' }} />
            </Button>

            <Button sx={{ minWidth: '10px' }} onClick={handleSubmit}>
              <CheckIcon style={{ color: 'green' }} />
            </Button>
          </div>

          <TextField
            multiline
            name='text'
            value={formData.text}
            onChange={handleChange}
            label='Text'
          />
        </>
      )}
    </div>
  )
}

export default ShowExerciseText
