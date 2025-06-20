import { Box, CircularProgress } from '@mui/material'
import React from 'react'

const LoadingSpinner: React.FC = () => {
  return (
    <Box
      display='flex'
      justifyContent='center'
      alignItems='center'
      height='100%'
      width='100%'
      minHeight='200px'
    >
      <CircularProgress />
    </Box>
  )
}

export default LoadingSpinner
