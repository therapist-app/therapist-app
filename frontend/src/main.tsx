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

createRoot(document.getElementById('root')!).render(
  <StrictMode>
    <Provider store={store}>
      <App />
    </Provider>
  </StrictMode>
)
