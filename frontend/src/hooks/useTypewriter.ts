import { useEffect, useState } from 'react'

export function useTypewriter(fullText: string | undefined, chunk = 5) {
  const [stream, setStream] = useState('')
  const [running, setRunning] = useState(false)

  useEffect(() => {
    if (!fullText) {
      return
    }

    let pos = 0
    setStream('')
    setRunning(true)

    const step = () => {
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

  return { stream: stream, running: running }
}
