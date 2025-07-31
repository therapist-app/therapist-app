import { SxProps, Theme } from '@mui/material/styles'

export const commonButtonStyles: SxProps<Theme> = {
  borderRadius: 20,
  textTransform: 'none',
  fontSize: '1rem',
  width: 'fit-content',
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
  width: 'fit-content',
  padding: '6px 24px',
  lineHeight: 1.75,
  backgroundColor: 'white',
  color: '#635BFF',
  border: '1px solid #635BFF',
  // Optional: keep height from shifting when focused (MUI often adds a 1px outline)
  boxSizing: 'border-box',
  '&:hover': {
    backgroundColor: '#fafafa',
    borderColor: '#554DE0',
  },
  '&:active': {
    backgroundColor: '#f2f2f2',
  },
  '&:disabled': {
    opacity: 0.5,
    backgroundColor: 'white',
    color: '#a3a0d9',
    borderColor: '#a3a0d9',
  },
  margin: 1,
}

export const deleteButtonStyles: SxProps<Theme> = {
  borderRadius: 20,
  textTransform: 'none',
  fontSize: '1rem',
  width: 'fit-content',
  padding: '6px 24px',
  lineHeight: 1.75,
  backgroundColor: '#d32f2f',
  backgroundImage: 'linear-gradient(45deg, #d32f2f 30%, #ff1744 90%)',
  boxShadow: '0 3px 5px 2px rgba(211, 47, 47, .3)',
  color: 'white',
  '&:hover': {
    backgroundColor: '#ff1744',
  },
  margin: 1,
}

export const deleteDisabledButtonStyles: SxProps<Theme> = {
  ...deleteButtonStyles,
  backgroundImage: 'none',
  backgroundColor: '#e0e0e0',
  color: '#ffffff',
  boxShadow: 'none',
  '&:hover': {
    backgroundColor: '#e0e0e0',
  },
}

export const successButtonStyles: SxProps<Theme> = {
  borderRadius: 20,
  textTransform: 'none',
  fontSize: '1rem',
  width: 'fit-content',
  padding: '6px 24px',
  lineHeight: 1.75,
  backgroundColor: '#2e7d32',
  backgroundImage: 'linear-gradient(45deg, #3fa444ff 30%, #00c853 90%)',
  boxShadow: '0 3px 5px 2px rgba(46, 125, 50, .3)',
  color: 'white',
  '&:hover': {
    backgroundColor: '#00c853',
  },
  margin: 1,
}

export const successDisabledButtonStyles: SxProps<Theme> = {
  ...successButtonStyles,
  backgroundImage: 'none',
  backgroundColor: '#bdbdbd',
  boxShadow: 'none',
  '&:hover': {
    backgroundColor: '#bdbdbd',
  },
}
