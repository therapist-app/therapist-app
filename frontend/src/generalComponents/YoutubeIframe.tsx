interface YoutubeIframeProps {
  youtubeUrl: string | undefined
}

const getYoutubeEmbedUrl = (urlOrId: string | undefined): string | undefined => {
  if (!urlOrId || typeof urlOrId !== 'string') {
    return undefined
  }

  const youtubeIdRegex =
    /(?:youtube\.com\/(?:[^/]+\/.+\/|(?:v|e(?:mbed)?)\/|.*[?&]v=)|youtu\.be\/)([^"&?/\s]{11})/

  const match = urlOrId.match(youtubeIdRegex)

  if (match && match[1]) {
    const videoId = match[1]
    return `https://www.youtube.com/embed/${videoId}`
  }

  if (urlOrId.length === 11 && !urlOrId.includes(' ')) {
    return `https://www.youtube.com/embed/${urlOrId}`
  }

  return undefined
}

const YoutubeIframe: React.FC<YoutubeIframeProps> = ({ youtubeUrl }) => {
  return (
    <iframe
      width='560'
      height='315'
      src={getYoutubeEmbedUrl(youtubeUrl)}
      allow='accelerometer; autoplay; clipboard-write; encrypted-media; gyroscope; picture-in-picture; web-share'
    ></iframe>
  )
}

export default YoutubeIframe
