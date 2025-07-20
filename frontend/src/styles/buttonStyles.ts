import { SxProps, Theme } from '@mui/material/styles'

export const commonButtonStyles: SxProps<Theme> = {
  borderRadius: 20,
  textTransform: 'none',
  fontSize: '1rem',
  minWidth: '130px',
  maxWidth: '130px',
  padding: '6px 24px',
  lineHeight: 1.75,
  backgroundColor: '#635BFF',
  backgroundImage: 'linear-gradient(45deg, #635BFF 30%, #7C4DFF 90%)',
  boxShadow: '0 3px 5px 2px rgba(99, 91, 255, .3)',
  color: 'white',
  '&:hover': {
    backgroundColor: '#7C4DFF',
  },
  margin: 1,
}

export const disabledButtonStyles: SxProps<Theme> = {
  ...commonButtonStyles,
  backgroundImage: 'none',
  backgroundColor: 'lightgrey',
  boxShadow: 'none',
  '&:hover': {
    backgroundColor: 'lightgrey',
  },
}

export const cancelButtonStyles: SxProps<Theme> = {
  borderRadius: 20,
  textTransform: 'none',
  fontSize: '1rem',
  minWidth: '130px',
  maxWidth: '130px',
  padding: '6px 24px',
  lineHeight: 1.75,
  backgroundColor: 'white',
  color: '#635BFF',
  '&:hover': {
    backgroundColor: '#f0f0f0',
  },
  margin: 1,
}
