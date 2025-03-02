import { StrictMode } from 'react'
import { createRoot } from 'react-dom/client'
import store from './store/store.ts'
import { Provider } from 'react-redux'
import './index.css'
import App from './App.tsx'
import './i18n'
import '@fontsource/roboto/300.css'
import '@fontsource/roboto/400.css'
import '@fontsource/roboto/500.css'
import '@fontsource/roboto/700.css'
import { ThemeProvider } from '@mui/material'
import theme from './muiTheme.ts'

createRoot(document.getElementById('root')!).render(
  <StrictMode>
    <ThemeProvider theme={theme}>
      <Provider store={store}>
        <App />
      </Provider>
    </ThemeProvider>
  </StrictMode>
)
