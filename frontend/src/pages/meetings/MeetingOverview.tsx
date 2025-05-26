import { ReactElement } from 'react'

import Layout from '../../generalComponents/Layout'
import MeetingOverviewComponent from './components/MeetingOverviewComponent'

const MeetingOverview = (): ReactElement => {
  return (
    <Layout>
      <MeetingOverviewComponent />
    </Layout>
  )
}

export default MeetingOverview
