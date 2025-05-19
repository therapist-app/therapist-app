import React, { useEffect, useState } from 'react'

interface ImageComponentProps {
  imageFile: File | undefined
}

const ImageComponent: React.FC<ImageComponentProps> = ({ imageFile }) => {
  const [imageUrl, setImageUrl] = useState<string | null>(null)

  useEffect(() => {
    if (!(imageFile instanceof Blob)) {
      setImageUrl(null)
      return
    }

    const url = URL.createObjectURL(imageFile)
    setImageUrl(url)

    return (): void => URL.revokeObjectURL(url)
  }, [imageFile])

  return <div>{imageUrl && <img src={imageUrl} alt='Uploaded' style={{ maxWidth: '100%' }} />}</div>
}

export default ImageComponent
