import React from 'react'

export const formatResponse = (text: string): React.ReactNode => {
  const lines = text.split('\n')
  const out: React.ReactNode[] = []

  let buf: React.ReactNode[] = []
  let list: 'ul' | 'ol' | null = null
  let olStart = 0

  const flush = (): void => {
  if (!list) {
    return
  }
  const Tag = (list === 'ul' ? 'ul' : 'ol') as React.ElementType
  out.push(
    <Tag
      key={out.length}
      {...(list === 'ol' ? { start: olStart + 1 } : undefined)}
      style={{
        margin: '0.75em 0',
        paddingLeft: 20,
        listStyleType: list === 'ol' ? 'decimal' : 'disc',
      }}
    >
      {buf}
    </Tag>
  )
  if (list === 'ol') {
    olStart += buf.length
  }
  buf = []
  list = null
}


  const inline = (s: string): React.ReactNode[] => {
    const arr: React.ReactNode[] = []
    const rx = /(\*\*[^*]+?\*\*)|(\*[^*\s][^*]*?\*)/g
    let last = 0,
      m: RegExpExecArray | null
    while ((m = rx.exec(s))) {
      if (m.index > last) {
        arr.push(s.slice(last, m.index))
      }
      const token = m[0]
      arr.push(
        token.startsWith('**') ? (
          <strong key={m.index}>{token.slice(2, -2)}</strong>
        ) : (
          <em key={m.index}>{token.slice(1, -1)}</em>
        )
      )
      last = m.index + token.length
    }
    if (last < s.length) {
      arr.push(s.slice(last))
    }
    return arr
  }

  lines.forEach((raw, i) => {
    const line = raw.trimEnd()
    if (!line.trim()) {
      flush()
      return
    }

    const h = line.match(/^\s*(#{1,6})\s+(.*)$/)
    if (h) {
      flush()
      const H = `h${h[1].length}` as React.ElementType
      out.push(
        <H key={i} style={{ margin: '1em 0 0.5em' }}>
          {inline(h[2])}
        </H>
      )
      return
    }

    const ol = line.match(/^\s*\d+\.\s+(.*)$/)
    if (ol) {
      if (list !== 'ol') {
        flush()
        list = 'ol'
      }
      buf.push(
        <li key={i} style={{ margin: '0.4em 0' }}>
          {inline(ol[1])}
        </li>
      )
      return
    }

    const ul = line.match(/^\s*[-*]\s+(.*)$/)
    if (ul) {
      if (list !== 'ul') {
        flush()
        list = 'ul'
      }
      buf.push(
        <li key={i} style={{ margin: '0.4em 0' }}>
          {inline(ul[1])}
        </li>
      )
      return
    }

    flush()
    out.push(
      <p key={i} style={{ margin: '0.75em 0', lineHeight: 1.5 }}>
        {inline(line.trimStart())}
      </p>
    )
  })

  flush()
  return <div>{out}</div>
}
