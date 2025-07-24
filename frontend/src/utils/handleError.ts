import { isAxiosError } from 'axios'

interface ErrorResponseData {
  detail?: string
  message?: string
  error?: string
  title?: string
  errors?: unknown
  [key: string]: unknown
}

const hasStringMessage = (e: unknown): e is { message: string } =>
  typeof e === 'object' &&
  e !== null &&
  'message' in e &&
  typeof (e as { message: unknown }).message === 'string'

export const handleError = async (err: unknown): Promise<string> => {
  if (isAxiosError(err)) {
    const res = err.response
    if (res) {
      const data = res.data

      if (typeof data === 'string') {
        return data
      }

      if (data instanceof Blob || data instanceof ArrayBuffer) {
        try {
          const text = data instanceof Blob ? await data.text() : arrayBufferToText(data)
          try {
            const obj = JSON.parse(text) as ErrorResponseData
            return pickMessageFromObject(obj) ?? text
          } catch {
            return text
          }
        } catch {
          return `HTTP ${res.status}`
        }
      }

      if (typeof data === 'object' && data !== null) {
        const obj = data as ErrorResponseData
        return pickMessageFromObject(obj) ?? `HTTP ${res.status}\n${JSON.stringify(obj, null, 2)}`
      }

      return `HTTP ${res.status}`
    }

    if (err.message && /Network Error/i.test(err.message)) {
      return 'The server cannot be reached. Did you start it?'
    }
    return err.message || 'An unknown error occurred'
  }

  if (typeof err === 'string') {
    return err
  }

  if (hasStringMessage(err)) {
    return err.message
  }

  return 'An unknown error occurred'
}

const pickMessageFromObject = (o: ErrorResponseData): string | undefined =>
  o.detail || o.message || o.error || o.title

const arrayBufferToText = (ab: ArrayBuffer): string =>
  new TextDecoder('utf-8').decode(new Uint8Array(ab))
