import CheckIcon from '@mui/icons-material/Check'
import ClearIcon from '@mui/icons-material/Clear'
import DeleteIcon from '@mui/icons-material/Delete'
import EditIcon from '@mui/icons-material/Edit'
import { Button, MenuItem, TextField, Typography } from '@mui/material'
import { useState } from 'react'

import { ExerciseComponentOutputDTO, UpdateExerciseComponentDTO } from '../../../api'
import { deleteExerciseComponent, updateExerciseComponent } from '../../../store/exerciseSlice'
import { useAppDispatch } from '../../../utils/hooks'

interface ShowExerciseTextComponentProps {
  exerciseComponent: ExerciseComponentOutputDTO
  numberOfExercises: number
  refresh(): void
}

const ShowExerciseTextComponent: React.FC<ShowExerciseTextComponentProps> = (
  props: ShowExerciseTextComponentProps
) => {
  const { exerciseComponent } = props
  const dispatch = useAppDispatch()

  const originalFormData: UpdateExerciseComponentDTO = {
    id: exerciseComponent.id ?? '',
    description: exerciseComponent.description,
    orderNumber: exerciseComponent.orderNumber,
  }

  const [formData, setFormData] = useState<UpdateExerciseComponentDTO>({
    id: exerciseComponent.id ?? '',
    description: exerciseComponent.description,
    orderNumber: exerciseComponent.orderNumber,
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
      await dispatch(updateExerciseComponent(formData)).unwrap()
      setIsEditing(false)
      props.refresh()
    } catch (err) {
      console.error(err)
    }
  }

  const clickDelete = async (): Promise<void> => {
    await dispatch(deleteExerciseComponent(exerciseComponent.id ?? ''))

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
          <div style={{ display: 'flex', gap: '10px', alignItems: 'center' }}>
            <Typography variant='h6'>{exerciseComponent.orderNumber}.</Typography>

            <Typography variant='h6'>Text</Typography>

            <Button sx={{ minWidth: '10px' }} onClick={clickEdit}>
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
            {exerciseComponent.description}
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
            name='description'
            value={formData.description}
            onChange={handleChange}
            label='Text'
          />
        </>
      )}
    </div>
  )
}

export default ShowExerciseTextComponent
