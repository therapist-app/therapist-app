export interface PsychologicalTestQuestionOutputDTO {
  question: string
  score: number
}

export interface PsychologicalTestOutputDTO {
  id: string
  name: string
  description: string
  patientId: string
  completedAt: string
  questions: PsychologicalTestQuestionOutputDTO[]
}
