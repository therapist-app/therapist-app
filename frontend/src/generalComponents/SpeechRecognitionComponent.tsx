import React, { FC, MouseEventHandler, useEffect, useRef, useState } from 'react'

// Type Definitions
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

// Component Props
interface SpeechToTextProps {
  value: string
  onChange: (newValue: string) => void
  language?: string
  placeholder?: string
}

// Style definitions
const styles: { [key: string]: React.CSSProperties } = {
  container: {
    maxWidth: '600px',

    fontFamily: '"Segoe UI", Roboto, Helvetica, Arial, sans-serif',
    backgroundColor: '#ffffff',
    borderRadius: '12px',
    boxShadow: '0 4px 12px rgba(0, 0, 0, 0.1)',
  },
  textareaContainer: {
    marginTop: '12px',
    marginBottom: '12px',
    marginRight: '12px',
    display: 'flex',
    gap: '15px',
  },
  textarea: {
    width: '100%',
    padding: '12px',
    border: '1px solid #d1d5db', // gray-300
    borderRadius: '8px',
    minHeight: '150px',
    fontSize: '16px',
    color: '#1f2937', // gray-800
    boxSizing: 'border-box',
    lineHeight: '1.5',
    transition: 'border-color 0.2s, box-shadow 0.2s',
  },
  textareaFocus: {
    // Note: pseudo-classes like :focus need JS or different approach
    borderColor: '#3b82f6', // blue-500
    boxShadow: '0 0 0 2px rgba(59, 130, 246, 0.4)',
  },
  textareaListening: {
    backgroundColor: '#f3f4f6', // gray-100
    cursor: 'not-allowed',
  },
  buttonContainer: {
    display: 'flex',
    flexDirection: 'column',
    justifyContent: 'center',
    alignItems: 'center',
    gap: '16px',
  },
  button: {
    padding: '12px 20px',
    fontSize: '16px',
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
  },
  startButton: {
    backgroundColor: '#22c55e', // green-500
    color: 'white',
  },
  stopButton: {
    backgroundColor: '#ef4444', // red-500
    color: 'white',
  },
  disabledButton: {
    opacity: 0.6,
    cursor: 'not-allowed',
    boxShadow: 'none',
  },
  errorMessage: {
    padding: '12px 16px',
    backgroundColor: '#fee2e2', // red-100
    border: '1px solid #fecaca', // red-200
    color: '#b91c1c', // red-700
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
    color: '#4b5563', // gray-600
    marginTop: '20px',
    fontSize: '14px',
  },
  compatibilityMessage: {
    padding: '12px 16px',
    backgroundColor: '#fef3c7', // amber-100
    border: '1px solid #fde68a', // amber-200
    color: '#92400e', // amber-700
    borderRadius: '8px',
    marginTop: '20px',
  },
  // For the wrapper example
  speechToTextContainerWrapper: {
    backgroundColor: '#f0f2f5', // A light gray background
    minHeight: '100vh',
    paddingTop: '40px',
    paddingBottom: '40px',
    display: 'flex',
    alignItems: 'center',
    justifyContent: 'center',
    boxSizing: 'border-box',
  },
}

const SpeechToTextComponent: FC<SpeechToTextProps> = ({
  value,
  onChange,
  language = 'en-US',
  placeholder = 'Speak or type here...',
}) => {
  const [isListening, setIsListening] = useState<boolean>(false)
  const [error, setError] = useState<string | null>(null)
  const recognitionRef = useRef<ISpeechRecognition | null>(null)
  const initialStartRef = useRef(true)
  const finalizedTranscriptUpToLastEventRef = useRef<string>('')

  const valueRef = useRef(value)
  const onChangeRef = useRef(onChange)

  useEffect(() => {
    valueRef.current = value
  }, [value])

  useEffect(() => {
    onChangeRef.current = onChange
  }, [onChange])

  useEffect(() => {
    if (!BrowserSpeechRecognition) {
      setError('Web Speech API is not supported in this browser. Please try Chrome or Edge.')
      return
    }

    recognitionRef.current = new BrowserSpeechRecognition()
    const recognition = recognitionRef.current

    recognition.continuous = true
    recognition.interimResults = true
    recognition.lang = language

    recognition.onstart = (): void => {
      setIsListening(true)
      setError(null)
      console.log('Speech recognition started.')
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
      console.error('Speech recognition error:', event.error, event.message)
      let errorMessage = `Error: ${event.error}`
      // ... (error message formatting as before)
      if (event.error === 'no-speech') {
        errorMessage = 'No speech detected. Please try speaking a bit louder or clearer.'
      } else if (event.error === 'audio-capture') {
        errorMessage =
          'Microphone not available or permission denied. Please check your microphone settings and browser permissions.'
      } else if (event.error === 'not-allowed') {
        errorMessage =
          'Permission to use the microphone was denied or has not been granted. Please allow microphone access in your browser settings.'
      } else if (event.message) {
        errorMessage = `${errorMessage} (${event.message})`
      }
      setError(errorMessage)
      setIsListening(false)
    }

    recognition.onend = (): void => {
      setIsListening(false)
      console.log('Speech recognition ended.')
      // The text in the textarea (value prop) will reflect the last state from onresult.
      // If the last part was interim, it remains, editable by the user.
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
  }, [language])

  const handleStartListening = (event: React.MouseEvent<HTMLButtonElement>): void => {
    event.preventDefault()
    if (recognitionRef.current && !isListening) {
      try {
        let textToStartWith = valueRef.current

        if (!initialStartRef.current && textToStartWith.trim() !== '') {
          textToStartWith += '\n\n'
          onChangeRef.current(textToStartWith)
        }
        // Anchor the finalized transcript ref to the current text (including potential newlines)
        finalizedTranscriptUpToLastEventRef.current = textToStartWith

        setError(null)
        recognitionRef.current.start()
        initialStartRef.current = false
      } catch (e: unknown) {
        console.error('Error starting speech recognition:', e)
        const typedError = e as { message?: string }
        setError(`Could not start recognition: ${typedError.message || 'Unknown error'}`)
        setIsListening(false)
      }
    }
  }

  const handleStopListening = (event: React.MouseEvent<HTMLButtonElement>): void => {
    event.preventDefault()
    if (recognitionRef.current && isListening) {
      recognitionRef.current.stop()
    }
  }

  const handleTextChange = (event: React.ChangeEvent<HTMLTextAreaElement>): void => {
    if (!isListening) {
      const newText = event.target.value
      onChangeRef.current(newText)
      // If user edits, the finalized transcript should also reflect this manual change
      finalizedTranscriptUpToLastEventRef.current = newText
    }
  }

  // Combine base and conditional styles for textarea
  const textareaStyle = {
    ...styles.textarea,
    ...(isListening ? styles.textareaListening : {}),
  }

  // Combine base and conditional styles for buttons
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
      <div style={styles.textareaContainer}>
        <textarea
          value={value}
          onChange={handleTextChange}
          readOnly={isListening}
          placeholder={isListening ? 'Listening...' : placeholder}
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
            }} // green-600
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
            Start Listening
          </button>
          <button
            onClick={handleStopListening}
            disabled={!isListening}
            style={stopButtonStyle}
            onMouseEnter={(e) => {
              if (isListening) {
                e.currentTarget.style.backgroundColor = '#dc2626'
              }
            }} // red-600
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
            Stop Listening
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
        <p style={{ ...styles.infoMessage, color: '#16a34a' /* green-600 */, fontWeight: 500 }}>
          Listening... Speak into your microphone.
        </p>
      )}

      {!BrowserSpeechRecognition && !error && (
        <div role='alert' style={styles.compatibilityMessage}>
          <strong style={styles.errorTitle}>Browser Compatibility</strong>
          <p>
            Web Speech API is not supported in your browser. Please try Chrome or Edge for the best
            experience.
          </p>
        </div>
      )}
    </div>
  )
}

export default SpeechToTextComponent
