import { createTheme } from '@mui/material/styles'

const theme = createTheme({
  typography: {
    fontFamily: 'Roboto',
    button: {
      textTransform: 'none',
    },
    h1: {
      fontSize: '34px',
      lineHeight: '44px',
      fontWeight: 700,
    },
    h2: {
      fontSize: '28px',
      lineHeight: '44px',
      fontWeight: 700,
    },
    h3: {
      fontSize: '22px',
      lineHeight: '32px',
      fontWeight: 700,
    },
    h4: {
      fontSize: '20px',
      lineHeight: '20px',
      fontWeight: 700,
    },
    h5: {
      fontSize: '20px',
      lineHeight: '20px',
      fontWeight: 700,
    },
    body1: {
      fontSize: '16px',
      lineHeight: '24px',
      fontWeight: 400,
    },
  },
})

export default theme
