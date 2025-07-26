import React from 'react'
interface YoutubeIframeProps {
  youtubeUrl: string | undefined
}

const getYoutubeEmbedUrl = (urlOrId: string | undefined): string | undefined => {
  if (!urlOrId || typeof urlOrId !== 'string') {
    return undefined
  }

  let videoId: string | null = null

  const youtubeIdRegex =
    /(?:youtube\.com\/(?:[^/]+\/.+\/|(?:v|e(?:mbed)?)\/|.*[?&]v=)|youtu\.be\/)([^"&?/\s]{11})/

  const match = urlOrId.match(youtubeIdRegex)

  if (match && match[1]) {
    videoId = match[1]
  } else if (urlOrId.length === 11 && !urlOrId.includes(' ')) {
    videoId = urlOrId
  }

  if (videoId) {
    const origin = window.location.origin
    return `https://www.youtube.com/embed/${videoId}?origin=${origin}`
  }

  return undefined
}

const YoutubeIframe: React.FC<YoutubeIframeProps> = ({ youtubeUrl }) => {
  const embedUrl = getYoutubeEmbedUrl(youtubeUrl)

  if (!embedUrl) {
    return <div>Invalid YouTube URL provided.</div>
  }

  return (
    <iframe
      width='560'
      height='315'
      src={embedUrl}
      title='YouTube video player'
      allow='accelerometer; autoplay; clipboard-write; encrypted-media; gyroscope; picture-in-picture; web-share'
      allowFullScreen
    ></iframe>
  )
}

export default YoutubeIframe
