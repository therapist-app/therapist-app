import React, { useEffect, useState } from 'react'
import { useTranslation } from 'react-i18next'

import { useNotify } from '../hooks/useNotify'

interface ImageComponentProps {
  imageFile: File | undefined
}

const ImageComponent: React.FC<ImageComponentProps> = ({ imageFile }) => {
  const [imageUrl, setImageUrl] = useState<string | null>(null)
  const { notifyWarning, notifyError } = useNotify()
  const { t } = useTranslation()

  useEffect(() => {
    if (!imageFile) {
      setImageUrl(null)
      return
    }

    if (!(imageFile instanceof Blob)) {
      setImageUrl(null)
      notifyWarning(t('errors.invalid_image_file'))
      return
    }

    let url: string | undefined
    try {
      url = URL.createObjectURL(imageFile)
      setImageUrl(url)
    } catch (err) {
      notifyError(t('errors.could_not_preview_image'))
      setImageUrl(null)
    }

    return (): void => {
      if (url) {
        URL.revokeObjectURL(url)
      }
    }
  }, [imageFile, notifyWarning, notifyError, t])

  return <div>{imageUrl && <img src={imageUrl} alt='Uploaded' style={{ maxWidth: '100%' }} />}</div>
}

export default ImageComponent
