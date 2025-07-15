import React, { FC, useEffect, useRef, useState } from 'react'
import { useTranslation } from 'react-i18next'

interface SpeechRecognitionEvent extends Event {
  readonly resultIndex: number
  readonly results: SpeechRecognitionResultList
}

interface SpeechRecognitionResultList {
  readonly length: number
  item(index: number): SpeechRecognitionResult
  [index: number]: SpeechRecognitionResult
}

interface SpeechRecognitionResult {
  readonly isFinal: boolean
  readonly length: number
  item(index: number): SpeechRecognitionAlternative
  [index: number]: SpeechRecognitionAlternative
}

interface SpeechRecognitionAlternative {
  readonly transcript: string
  readonly confidence: number
}

interface SpeechRecognitionErrorEvent extends Event {
  readonly error: string
  readonly message: string
}

interface ISpeechRecognition {
  continuous: boolean
  interimResults: boolean
  lang: string
  onstart: (() => void) | null
  onresult: ((event: SpeechRecognitionEvent) => void) | null
  onerror: ((event: SpeechRecognitionErrorEvent) => void) | null
  onend: (() => void) | null
  start: () => void
  stop: () => void
  abort: () => void
}

declare global {
  interface Window {
    SpeechRecognition?: new () => ISpeechRecognition
    webkitSpeechRecognition?: new () => ISpeechRecognition
  }
}

const BrowserSpeechRecognition = window.SpeechRecognition || window.webkitSpeechRecognition

interface LanguageOption {
  code: string
  name: string
}

const defaultLanguages: LanguageOption[] = [
  { code: 'en-US', name: 'English (US)' },
  { code: 'en-GB', name: 'English (UK)' },
  { code: 'de-DE', name: 'Deutsch (Deutschland)' },
  { code: 'uk-UA', name: 'Українська (Україна)' },
  { code: 'fr-FR', name: 'Français (France)' },
  { code: 'it-IT', name: 'Italiano (Italia)' },
  { code: 'es-ES', name: 'Español (España)' },
  { code: 'pt-BR', name: 'Português (Brasil)' },
  { code: 'ja-JP', name: '日本語 (日本)' },
  { code: 'ko-KR', name: '한국어 (대한민국)' },
  { code: 'zh-CN', name: '中文 (简体)' },
]

interface SpeechToTextProps {
  value: string
  onChange: (newValue: string) => void
  startDirectly?: boolean
  language?: string
  availableLanguages?: LanguageOption[]
  placeholder?: string
}

const styles: { [key: string]: React.CSSProperties } = {
  container: {
    maxWidth: '600px',
    fontFamily: '"Segoe UI", Roboto, Helvetica, Arial, sans-serif',
    backgroundColor: '#ffffff',
    borderRadius: '12px',
    boxShadow: '0 4px 12px rgba(0, 0, 0, 0.1)',
    padding: '24px',
  },
  languageSelectContainer: {
    marginBottom: '20px',
    position: 'relative',
  },
  languageSelectLabel: {
    display: 'block',
    fontSize: '12px',
    color: '#6b7280',
    marginBottom: '4px',
    fontWeight: 500,
  },
  languageSelect: {
    width: '100%',
    padding: '10px 30px 10px 12px',
    fontSize: '16px',
    border: '1px solid #d1d5db',
    borderRadius: '8px',
    backgroundColor: '#fff',
    color: '#1f2937',
    appearance: 'none',
    backgroundImage: `url('data:image/svg+xml;utf8,<svg fill="%236b7280" height="24" viewBox="0 0 24 24" width="24" xmlns="http://www.w3.org/2000/svg"><path d="M7 10l5 5 5-5z"/><path d="M0 0h24v24H0z" fill="none"/></svg>')`,
    backgroundRepeat: 'no-repeat',
    backgroundPosition: 'right 10px center',
    cursor: 'pointer',
    boxSizing: 'border-box',
  },
  textareaContainer: {
    marginTop: '12px',
    marginBottom: '12px',
    display: 'flex',
    gap: '15px',
    alignItems: 'flex-start',
  },
  textarea: {
    flexGrow: 1,
    padding: '12px',
    border: '1px solid #d1d5db',
    borderRadius: '8px',
    minHeight: '150px',
    fontSize: '16px',
    color: '#1f2937',
    boxSizing: 'border-box',
    lineHeight: '1.5',
    transition: 'border-color 0.2s, box-shadow 0.2s',
  },
  textareaListening: {
    backgroundColor: '#f3f4f6',
    cursor: 'not-allowed',
  },
  buttonContainer: {
    display: 'flex',
    flexDirection: 'column',
    justifyContent: 'flex-start',
    alignItems: 'center',
    gap: '16px',
    flexShrink: 0,
  },
  button: {
    padding: '10px 15px',
    fontSize: '14px',
    fontWeight: 500,
    borderRadius: '8px',
    border: 'none',
    cursor: 'pointer',
    transition: 'background-color 0.2s ease-in-out, box-shadow 0.2s',
    display: 'flex',
    alignItems: 'center',
    justifyContent: 'center',
    gap: '8px',
    boxShadow: '0 2px 4px rgba(0,0,0,0.05)',
    width: '180px',
    textAlign: 'center',
  },
  startButton: {
    backgroundColor: '#22c55e',
    color: 'white',
  },
  stopButton: {
    backgroundColor: '#ef4444',
    color: 'white',
  },
  disabledButton: {
    opacity: 0.6,
    cursor: 'not-allowed',
    boxShadow: 'none',
  },
  errorMessage: {
    padding: '12px 16px',
    backgroundColor: '#fee2e2',
    border: '1px solid #fecaca',
    color: '#b91c1c',
    borderRadius: '8px',
    marginBottom: '20px',
    whiteSpace: 'pre-wrap',
  },
  errorTitle: {
    fontWeight: 'bold',
    display: 'block',
    marginBottom: '4px',
  },
  infoMessage: {
    textAlign: 'center',
    color: '#4b5563',
    marginTop: '20px',
    fontSize: '14px',
  },
  compatibilityMessage: {
    padding: '12px 16px',
    backgroundColor: '#fef3c7',
    border: '1px solid #fde68a',
    color: '#92400e',
    borderRadius: '8px',
    marginTop: '20px',
  },
}

const SpeechToTextComponent: FC<SpeechToTextProps> = ({
  value,
  onChange,
  startDirectly = false,
  language = 'en-US',
  availableLanguages = defaultLanguages,
  placeholder = '',
}) => {
  const { t } = useTranslation()
  const [isListening, setIsListening] = useState<boolean>(false)
  const [error, setError] = useState<string | null>(null)
  const [selectedLanguage, setSelectedLanguage] = useState<string>(language)

  const recognitionRef = useRef<ISpeechRecognition | null>(null)
  const initialStartRef = useRef(true)
  const finalizedTranscriptUpToLastEventRef = useRef<string>('')
  const textareaRef = useRef<HTMLTextAreaElement>(null)
  const [startDirectlyState, setStartDirectlyState] = useState(startDirectly)

  const valueRef = useRef(value)
  const onChangeRef = useRef(onChange)

  useEffect(() => {
    valueRef.current = value
    if (textareaRef.current) {
      textareaRef.current.scrollTop = textareaRef.current.scrollHeight
    }
  }, [value])

  useEffect(() => {
    onChangeRef.current = onChange
  }, [onChange])

  useEffect(() => {
    setSelectedLanguage(language)
  }, [language])

  useEffect(() => {
    if (!BrowserSpeechRecognition) {
      setError('Web Speech API is not supported in this browser. Please try Chrome or Edge.')
      return (): void => {
        if (recognitionRef.current) {
          recognitionRef.current.stop()
          recognitionRef.current.onstart = null
          recognitionRef.current.onresult = null
          recognitionRef.current.onerror = null
          recognitionRef.current.onend = null
          recognitionRef.current = null
        }
      }
    }

    if (recognitionRef.current) {
      recognitionRef.current.stop()
      recognitionRef.current.onstart = null
      recognitionRef.current.onresult = null
      recognitionRef.current.onerror = null
      recognitionRef.current.onend = null
      recognitionRef.current = null
    }

    recognitionRef.current = new BrowserSpeechRecognition()
    const recognition = recognitionRef.current

    recognition.continuous = true
    recognition.interimResults = true
    recognition.lang = selectedLanguage

    recognition.onstart = (): void => {
      setIsListening(true)
      setError(null)
    }

    recognition.onresult = (event: SpeechRecognitionEvent): void => {
      let interimForThisPass = ''
      let newlyFinalizedForThisPass = ''

      for (let i = event.resultIndex; i < event.results.length; ++i) {
        const transcriptPart = event.results[i][0].transcript
        if (event.results[i].isFinal) {
          newlyFinalizedForThisPass += transcriptPart + ' '
        } else {
          interimForThisPass += transcriptPart
        }
      }

      const currentBaseFinalized = finalizedTranscriptUpToLastEventRef.current
      const newTextValue = currentBaseFinalized + newlyFinalizedForThisPass + interimForThisPass
      onChangeRef.current(newTextValue)

      if (newlyFinalizedForThisPass.trim()) {
        finalizedTranscriptUpToLastEventRef.current =
          currentBaseFinalized + newlyFinalizedForThisPass
      }
    }

    recognition.onerror = (event: SpeechRecognitionErrorEvent): void => {
      let errorMessage = `Error: ${event.error}`
      if (event.error === 'no-speech') {
        errorMessage = t('meeting.error.no_speech_detected')
      } else if (event.error === 'audio-capture') {
        errorMessage = t('meeting.error.microphone_not_available')
      } else if (event.error === 'not-allowed') {
        errorMessage = t('meeting.error.not_allowed')
      } else if (event.message) {
        errorMessage = `${errorMessage} (${event.message})`
      }
      setError(errorMessage)
      setIsListening(false)
    }

    recognition.onend = (): void => {
      setIsListening(false)
    }

    return (): void => {
      if (recognitionRef.current) {
        recognitionRef.current.stop()
        recognitionRef.current.onstart = null
        recognitionRef.current.onresult = null
        recognitionRef.current.onerror = null
        recognitionRef.current.onend = null
        recognitionRef.current = null
      }
    }
  }, [selectedLanguage])

  useEffect(() => {
    if (startDirectlyState && !value && recognitionRef.current && !isListening) {
      try {
        let textToStartWith = valueRef.current

        if (!initialStartRef.current && textToStartWith.trim() !== '') {
          textToStartWith += '\n'
          onChangeRef.current(textToStartWith)
        }
        finalizedTranscriptUpToLastEventRef.current = textToStartWith

        setError(null)
        recognitionRef.current.start()
        initialStartRef.current = false
      } catch (e: unknown) {
        const typedError = e as { message?: string }
        setError(`Could not start recognition: ${typedError.message || 'Unknown error'}`)
        setIsListening(false)
      } finally {
        setStartDirectlyState(false)
      }
    }
  }, [isListening, startDirectlyState, value])

  const handleStartListening = (event?: React.MouseEvent<HTMLButtonElement>): void => {
    if (event) {
      event.preventDefault()
    }
    if (recognitionRef.current && !isListening) {
      try {
        let textToStartWith = valueRef.current

        if (!initialStartRef.current && textToStartWith.trim() !== '') {
          textToStartWith += '\n'
          onChangeRef.current(textToStartWith)
        }
        finalizedTranscriptUpToLastEventRef.current = textToStartWith

        setError(null)
        recognitionRef.current.start()
        initialStartRef.current = false
      } catch (e: unknown) {
        const typedError = e as { message?: string }
        setError(`Could not start recognition: ${typedError.message || 'Unknown error'}`)
        setIsListening(false)
      }
    }
  }

  const handleLanguageChange = (event: React.ChangeEvent<HTMLSelectElement>): void => {
    setSelectedLanguage(event.target.value)
  }

  const handleStopListening = (event: React.MouseEvent<HTMLButtonElement>): void => {
    setStartDirectlyState(false)
    event.preventDefault()
    if (recognitionRef.current && isListening) {
      const currentText = valueRef.current
      if (currentText.trim() !== '') {
        const textWithNewlines = currentText + '\n'
        onChangeRef.current(textWithNewlines)
        finalizedTranscriptUpToLastEventRef.current = textWithNewlines
      }
      recognitionRef.current.stop()
    }
  }

  const handleTextChange = (event: React.ChangeEvent<HTMLTextAreaElement>): void => {
    if (!isListening) {
      const newText = event.target.value
      onChangeRef.current(newText)
      finalizedTranscriptUpToLastEventRef.current = newText
    }
  }

  const textareaStyle = {
    ...styles.textarea,
    ...(isListening ? styles.textareaListening : {}),
  }

  const startButtonStyle = {
    ...styles.button,
    ...styles.startButton,
    ...(isListening || !BrowserSpeechRecognition ? styles.disabledButton : {}),
  }
  const stopButtonStyle = {
    ...styles.button,
    ...styles.stopButton,
    ...(!isListening ? styles.disabledButton : {}),
  }

  return (
    <div style={styles.container}>
      <div style={styles.languageSelectContainer}>
        <label htmlFor='language-select' style={styles.languageSelectLabel}>
          {t('meeting.language')}
        </label>
        <select
          id='language-select'
          value={selectedLanguage}
          onChange={handleLanguageChange}
          style={styles.languageSelect}
          disabled={isListening}
        >
          {availableLanguages.map((lang) => (
            <option key={lang.code} value={lang.code}>
              {lang.name}
            </option>
          ))}
        </select>
      </div>

      <div style={styles.textareaContainer}>
        <textarea
          ref={textareaRef}
          value={value}
          onChange={handleTextChange}
          readOnly={isListening}
          placeholder={isListening ? t("meeting.listening") : (placeholder || t('meeting.speak_or_type_here'))}
          style={textareaStyle}
          rows={6}
        />
        <div style={styles.buttonContainer}>
          <button
            onClick={handleStartListening}
            disabled={isListening || !BrowserSpeechRecognition}
            style={startButtonStyle}
            onMouseEnter={(e) => {
              if (!(isListening || !BrowserSpeechRecognition)) {
                e.currentTarget.style.backgroundColor = '#16a34a'
              }
            }}
            onMouseLeave={(e) => {
              if (!(isListening || !BrowserSpeechRecognition)) {
                e.currentTarget.style.backgroundColor = styles.startButton.backgroundColor as string
              }
            }}
          >
            <svg
              xmlns='http://www.w3.org/2000/svg'
              viewBox='0 0 24 24'
              fill='currentColor'
              style={{ width: '20px', height: '20px' }}
            >
              <path d='M8.25 4.5a3.75 3.75 0 1 1 7.5 0v8.25a3.75 3.75 0 1 1-7.5 0V4.5Z' />
              <path d='M6 10.5a.75.75 0 0 1 .75.75v1.5a5.25 5.25 0 1 0 10.5 0v-1.5a.75.75 0 0 1 1.5 0v1.5a6.751 6.751 0 0 1-6 6.709v2.041h3a.75.75 0 0 1 0 1.5h-7.5a.75.75 0 0 1 0-1.5h3v-2.041a6.751 6.751 0 0 1-6-6.709v-1.5A.75.75 0 0 1 6 10.5Z' />
            </svg>
            {t('meeting.start_listening')}
          </button>
          <button
            onClick={handleStopListening}
            disabled={!isListening}
            style={stopButtonStyle}
            onMouseEnter={(e) => {
              if (isListening) {
                e.currentTarget.style.backgroundColor = '#dc2626'
              }
            }}
            onMouseLeave={(e) => {
              if (isListening) {
                e.currentTarget.style.backgroundColor = styles.stopButton.backgroundColor as string
              }
            }}
          >
            <svg
              xmlns='http://www.w3.org/2000/svg'
              viewBox='0 0 24 24'
              fill='currentColor'
              style={{ width: '20px', height: '20px' }}
            >
              <path
                fillRule='evenodd'
                d='M4.5 7.5a3 3 0 0 1 3-3h9a3 3 0 0 1 3 3v9a3 3 0 0 1-3 3h-9a3 3 0 0 1-3-3v-9Z'
                clipRule='evenodd'
              />
            </svg>
            {t('meeting.stop_listening')}
          </button>
        </div>
      </div>

      {error && (
        <div role='alert' style={styles.errorMessage}>
          <strong style={styles.errorTitle}>Error</strong>
          <p>{error}</p>
        </div>
      )}

      {isListening && !error && (
        <p style={{ ...styles.infoMessage, color: '#16a34a', fontWeight: 500 }}>
          {t('meeting.listening_speak_into_microphone')}
        </p>
      )}

      {!BrowserSpeechRecognition && !error && (
        <div role='alert' style={styles.compatibilityMessage}>
          <strong style={styles.errorTitle}>{t('meeting.browser_compatibility')}</strong>
          <p>
            {t('meeting.web_search_api_not_supported')}
          </p>
        </div>
      )}
    </div>
  )
}

export default SpeechToTextComponent
