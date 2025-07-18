import { useEffect, useState } from 'react'

interface TypewriterResult {
  stream: string
  running: boolean
}

export function useTypewriter(
  fullText: string | undefined,
  chunk = 5
): TypewriterResult {
  const [stream, setStream] = useState<string>('')
  const [running, setRunning] = useState<boolean>(false)

  useEffect(() => {
    if (!fullText) {
      return
    }

    let pos = 0
    setStream('')
    setRunning(true)

    const step = (): void => {
      pos = Math.min(pos + chunk, fullText.length)
      setStream(fullText.slice(0, pos))
      if (pos < fullText.length) {
        requestAnimationFrame(step)
      } else {
        setRunning(false)
      }
    }

    requestAnimationFrame(step)
  }, [fullText, chunk])

  return { stream, running }
}
