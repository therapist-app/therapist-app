import { Divider } from '@mui/material'
import { ReactElement } from 'react'

export default function CustomizedDivider(): ReactElement {
  return <Divider sx={{ margin: '40px 0', borderBottomWidth: '1px', borderColor: 'black' }} />
}
