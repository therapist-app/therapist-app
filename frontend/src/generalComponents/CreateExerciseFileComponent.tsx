import { useParams } from 'react-router-dom'

import {
  CreateExerciseComponentDTO,
  CreateExerciseComponentDTOExerciseComponentTypeEnum,
} from '../api'
import { createExerciseComponent } from '../store/exerciseSlice'
import { useAppDispatch } from '../utils/hooks'
import FileUpload from './FileUpload'

interface CreateExerciseFileComponentProps {
  createdExerciseFile(): void
}

const CreateExerciseFileComponent: React.FC<CreateExerciseFileComponentProps> = (
  props: CreateExerciseFileComponentProps
) => {
  const { exerciseId } = useParams()
  const dispatch = useAppDispatch()

  const handleUpload = async (file: File): Promise<void> => {
    try {
      const createExerciseComponentDTO: CreateExerciseComponentDTO = {
        exerciseId: exerciseId ?? '',
        exerciseComponentType: CreateExerciseComponentDTOExerciseComponentTypeEnum.Text,
        description: 'Test',
      }
      await dispatch(
        createExerciseComponent({
          createExerciseComponentDTO: createExerciseComponentDTO,
          file: file,
        })
      )
    } catch (err) {
      console.error('Registration error:', err)
    } finally {
      props.createdExerciseFile()
    }
  }

  return <FileUpload onUpload={handleUpload} />
}

export default CreateExerciseFileComponent
