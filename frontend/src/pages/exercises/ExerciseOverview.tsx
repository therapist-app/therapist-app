import { ReactElement } from 'react'

import Layout from '../../generalComponents/Layout'
import ExerciseOverviewComponent from './components/ExerciseOverviewComponent'

const ExerciseOverview = (): ReactElement => {
  return (
    <Layout>
      <ExerciseOverviewComponent />
    </Layout>
  )
}

export default ExerciseOverview
