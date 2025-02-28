import { Typography } from '@mui/material'
import { useParams } from 'react-router-dom'

const ChatBotTemplateEdit = () => {
  const { chatBotTemplateId } = useParams()
  return (
    <div>
      <Typography variant='h3'>
        This page is for editing the bot template with ID: "{chatBotTemplateId}"
      </Typography>
    </div>
  )
}

export default ChatBotTemplateEdit
