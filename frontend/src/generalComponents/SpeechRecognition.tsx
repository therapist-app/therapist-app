// You might want to place these type definitions in a separate .d.ts file
// or at the top of your .tsx file.

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
  // In older specs, this was SpeechRecognitionError
  readonly error: string // Common errors: 'no-speech', 'audio-capture', 'not-allowed', 'network', 'aborted', etc.
  readonly message: string
}

// This is a simplified interface. The actual SpeechRecognition type is more complex.
// You might find more complete typings in community packages like @types/dom-speech-recognition
interface ISpeechRecognition {
  continuous: boolean
  interimResults: boolean
  lang: string
  onstart: (() => void) | null
  onresult: ((event: SpeechRecognitionEvent) => void) | null
  onerror: ((event: SpeechRecognitionErrorEvent) => void) | null // Updated to SpeechRecognitionErrorEvent
  onend: (() => void) | null
  start: () => void
  stop: () => void
  abort: () => void // Added abort for completeness
}

// Extend Window interface to include prefixed SpeechRecognition
declare global {
  interface Window {
    SpeechRecognition: typeof SpeechRecognition | undefined
    webkitSpeechRecognition: typeof SpeechRecognition | undefined
  }
}

const BrowserSpeechRecognition = window.SpeechRecognition || window.webkitSpeechRecognition

import React, { FC, useEffect, useRef, useState } from 'react'

// (You can include the type definitions from above here if not in a separate file)

interface SpeechToTextProps {
  language?: string // Make language prop optional with a default
}

const SpeechToText: FC<SpeechToTextProps> = ({ language = 'en-US' }) => {
  const [isListening, setIsListening] = useState<boolean>(false)
  const [transcript, setTranscript] = useState<string>('')
  const [interimTranscript, setInterimTranscript] = useState<string>('')
  const [error, setError] = useState<string | null>(null)

  const recognitionRef = useRef<ISpeechRecognition | null>(null)

  useEffect(() => {
    if (!BrowserSpeechRecognition) {
      setError('Web Speech API is not supported in this browser. Please try Chrome or Edge.')
      return
    }

    recognitionRef.current = new BrowserSpeechRecognition() as ISpeechRecognition
    const recognition = recognitionRef.current

    recognition.continuous = true
    recognition.interimResults = true
    recognition.lang = language

    recognition.onstart = (): void => {
      setIsListening(true)
      setError(null)
      setTranscript('')
      setInterimTranscript('')
      console.log('Speech recognition started.')
    }

    recognition.onresult = (event: SpeechRecognitionEvent): void => {
      let final = ''
      let interim = ''
      for (let i = event.resultIndex; i < event.results.length; ++i) {
        if (event.results[i].isFinal) {
          final += event.results[i][0].transcript + ' '
        } else {
          interim += event.results[i][0].transcript
        }
      }
      // Use functional updates for state that depends on previous state
      setTranscript((prev) => prev + final)
      setInterimTranscript(interim)
    }

    recognition.onerror = (event: SpeechRecognitionErrorEvent): void => {
      // Type the event
      console.error('Speech recognition error:', event.error, event.message)
      let errorMessage = `Error: ${event.error}`
      if (event.error === 'no-speech') {
        errorMessage = 'No speech detected. Please try speaking a bit louder or clearer.'
      } else if (event.error === 'audio-capture') {
        errorMessage =
          'Microphone not available or permission denied. Please check your microphone settings and browser permissions.'
      } else if (event.error === 'not-allowed') {
        errorMessage =
          'Permission to use the microphone was denied or has not been granted. Please allow microphone access in your browser settings.'
      } else if (event.message) {
        errorMessage = `${errorMessage} - ${event.message}`
      }
      setError(errorMessage)
      setIsListening(false)
    }

    recognition.onend = (): void => {
      setIsListening(false)
      setInterimTranscript('')
      console.log('Speech recognition ended.')
    }

    // Cleanup function
    return (): void => {
      if (recognitionRef.current) {
        recognitionRef.current.stop()
        // Optional: Nullify event handlers to prevent memory leaks in some edge cases
        recognitionRef.current.onstart = null
        recognitionRef.current.onresult = null
        recognitionRef.current.onerror = null
        recognitionRef.current.onend = null
      }
    }
  }, [language]) // Re-run effect if language prop changes

  const handleStartListening = (): void => {
    if (recognitionRef.current && !isListening) {
      try {
        setTranscript('')
        setInterimTranscript('')
        setError(null) // Clear previous errors
        recognitionRef.current.start()
      } catch (e: unknown) {
        console.error('Error starting speech recognition:', e)
        const errorMessage =
          typeof e === 'object' && e !== null && 'message' in e
            ? (e as { message?: string }).message || 'Unknown error'
            : 'Unknown error'
        setError(`Could not start recognition: ${errorMessage}`)
        setIsListening(false)
      }
    }
  }

  const handleStopListening = (): void => {
    if (recognitionRef.current && isListening) {
      recognitionRef.current.stop()
    }
  }

  return (
    <div
      style={{
        fontFamily: 'sans-serif',
        maxWidth: '600px',
        margin: '50px auto',
        textAlign: 'center',
      }}
    >
      <h2>React Speech-to-Text (TypeScript)</h2>
      <div style={{ marginBottom: '20px' }}>
        <button
          onClick={handleStartListening}
          disabled={isListening || !BrowserSpeechRecognition}
          style={{ padding: '10px 15px', marginRight: '10px', fontSize: '16px', cursor: 'pointer' }}
        >
          Start Listening 🎤
        </button>
        <button
          onClick={handleStopListening}
          disabled={!isListening}
          style={{ padding: '10px 15px', fontSize: '16px', cursor: 'pointer' }}
        >
          Stop Listening 🛑
        </button>
      </div>

      {error && (
        <p style={{ color: 'red', marginTop: '10px', fontWeight: 'bold', whiteSpace: 'pre-wrap' }}>
          {error}
        </p>
      )}

      {isListening && !error && (
        <p style={{ color: 'green', marginTop: '10px' }}>
          Listening... Speak into your microphone.
        </p>
      )}

      {(transcript || interimTranscript) && (
        <div
          style={{
            border: '1px solid #ccc',
            padding: '15px',
            minHeight: '100px',
            textAlign: 'left',
            marginTop: '20px',
            whiteSpace: 'pre-wrap',
          }}
        >
          <p>
            <strong>Transcript:</strong>
          </p>
          <p>
            {transcript}
            {interimTranscript && <em style={{ color: '#777' }}>{interimTranscript}</em>}
          </p>
        </div>
      )}

      {!BrowserSpeechRecognition &&
        !error && ( // Show only if no other error is more prominent
          <p style={{ color: 'orange', marginTop: '20px' }}>
            Web Speech API is not supported in your browser. Please try Chrome or Edge.
          </p>
        )}
    </div>
  )
}

export default SpeechToText
