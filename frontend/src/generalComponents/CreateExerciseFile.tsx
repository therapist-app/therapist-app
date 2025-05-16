import { useParams } from 'react-router-dom'

import { CreateExerciseFileDTO } from '../api'
import { createExerciseFile } from '../store/exerciseSlice'
import { useAppDispatch } from '../utils/hooks'
import FileUpload from './FileUpload'

interface CreateExerciseFileProps {
  createdExerciseFile(): void
}

const CreateExerciseFile: React.FC<CreateExerciseFileProps> = (props: CreateExerciseFileProps) => {
  const { exerciseId } = useParams()
  const dispatch = useAppDispatch()

  const handleUpload = async (file: File): Promise<void> => {
    try {
      const createExerciseFileDTO: CreateExerciseFileDTO = {
        exerciseId: exerciseId ?? '',
        description: 'Test',
      }
      await dispatch(
        createExerciseFile({ createExerciseFileDTO: createExerciseFileDTO, file: file })
      )
    } catch (err) {
      console.error('Registration error:', err)
    } finally {
      props.createdExerciseFile()
    }
  }

  return <FileUpload onUpload={handleUpload} />
}

export default CreateExerciseFile
