import MoreVertIcon from '@mui/icons-material/MoreVert'
import {
  Alert,
  Box,
  Button,
  Card,
  CardActionArea,
  CardContent,
  Chip,
  IconButton,
  Menu,
  MenuItem,
  Snackbar,
  Tooltip,
  Typography,
} from '@mui/material'
import { AxiosError } from 'axios'
import React, { ReactElement, useEffect, useState } from 'react'
import { useTranslation } from 'react-i18next'
import { TbMessageChatbot } from 'react-icons/tb'
import { useSelector } from 'react-redux'
import { useNavigate, useParams } from 'react-router-dom'

import { ChatbotTemplateOutputDTO, CreateChatbotTemplateDTO } from '../../api'
import {
  cloneChatbotTemplate,
  clonePatientChatbotTemplate,
  createChatbotTemplate,
  createPatientChatbotTemplate,
  deleteChatbotTemplate,
  deletePatientChatbotTemplate,
} from '../../store/chatbotTemplateSlice'
import { getAllPatientsOfTherapist } from '../../store/patientSlice'
import { RootState } from '../../store/store'
import { getCurrentlyLoggedInTherapist } from '../../store/therapistSlice'
import { commonButtonStyles } from '../../styles/buttonStyles'
import { handleError } from '../../utils/handleError'
import { useAppDispatch } from '../../utils/hooks'
import { getPathFromPage, PAGES } from '../../utils/routes'

const ChatbotOverview = (): ReactElement => {
  const { t } = useTranslation()
  const { patientId } = useParams<{ patientId: string }>()
  const navigate = useNavigate()
  const dispatch = useAppDispatch()

  const therapist = useSelector((s: RootState) => s.therapist.loggedInTherapist)
  const patient = useSelector((s: RootState) =>
    patientId ? s.patient.allPatientsOfTherapist.find((p) => p.id === patientId) : undefined
  )

  const patientTemplates: ChatbotTemplateOutputDTO[] = patient?.chatbotTemplatesOutputDTO ?? []
  const therapistTemplates: ChatbotTemplateOutputDTO[] = (
    therapist?.chatbotTemplatesOutputDTO ?? []
  ).filter((tpl) => tpl.patientId == null)
  const [snackbar, setSnackbar] = useState({
    open: false,
    message: '',
    severity: 'info' as 'info' | 'success' | 'error' | 'warning',
  })
  const openSnackbar = (msg: string, sev: typeof snackbar.severity = 'info'): void =>
    setSnackbar({ open: true, message: msg, severity: sev })

  const [anchorEl, setAnchorEl] = useState<null | HTMLElement>(null)
  const [currentChatbot, setCurrentChatbot] = useState<ChatbotTemplateOutputDTO | null>(null)

  const isPatientContext = Boolean(patientId)
  const onlyOnePatientTemplateLeft = isPatientContext && patientTemplates.length === 1

  useEffect(() => {
    if (patientId) {
      dispatch(getAllPatientsOfTherapist())
    }
    dispatch(getCurrentlyLoggedInTherapist())
  }, [dispatch, patientId])

  const iconFor = (icon: string): ReactElement | null =>
    (
      ({
        Chatbot: <TbMessageChatbot />,
      }) as Record<string, ReactElement | null>
    )[icon] ?? null

  const openTemplate = (bot: ChatbotTemplateOutputDTO): void => {
    navigate(
      getPathFromPage(PAGES.CHATBOT_TEMPLATES_DETAILS_PAGE, {
        chatbotTemplateId: bot.id!,
      }),
      {
        state: {
          chatbotConfig: bot,
          ...(patientId && { patientId: patientId }),
        },
      }
    )
  }

  const handleCreateChatbot = async (): Promise<void> => {
    try {
      const dto: CreateChatbotTemplateDTO = {
        chatbotName: '',
        chatbotIcon: 'Chatbot',
        chatbotRole: 'FAQ',
        chatbotTone: 'friendly',
        welcomeMessage: 'Hello! How can I assist you today?',
      }

      const created = patientId
        ? await dispatch(createPatientChatbotTemplate({ patientId: patientId, dto: dto })).unwrap()
        : await dispatch(createChatbotTemplate(dto)).unwrap()

      if (patientId) {
        dispatch(getAllPatientsOfTherapist())
      } else {
        dispatch(getCurrentlyLoggedInTherapist())
      }

      navigate(
        getPathFromPage(PAGES.CHATBOT_TEMPLATES_DETAILS_PAGE, {
          chatbotTemplateId: created.id!,
        }),
        {
          state: { patientId: patientId, chatbotConfig: created },
        }
      )
    } catch (err) {
      openSnackbar(handleError(err as AxiosError), 'error')
    }
  }

  const handleCreateFromTherapistTemplate = async (
    tpl: ChatbotTemplateOutputDTO
  ): Promise<void> => {
    if (!patientId) {
      return
    }

    try {
      const created = await dispatch(
        clonePatientChatbotTemplate({
          patientId: patientId,
          templateId: tpl.id!,
        })
      ).unwrap()

      dispatch(getAllPatientsOfTherapist())

      navigate(
        getPathFromPage(PAGES.CHATBOT_TEMPLATES_DETAILS_PAGE, {
          chatbotTemplateId: created.id!,
        }),
        { state: { patientId: patientId, chatbotConfig: created } }
      )
    } catch (err) {
      openSnackbar(handleError(err as AxiosError), 'error')
    }
  }

  const handleMenu = (
    e: React.MouseEvent<HTMLButtonElement>,
    bot: ChatbotTemplateOutputDTO
  ): void => {
    setAnchorEl(e.currentTarget)
    setCurrentChatbot(bot)
  }
  const closeMenu = (): void => setAnchorEl(null)

  const handleClone = async (): Promise<void> => {
    if (!currentChatbot) {
      return
    }
    try {
      if (patientId) {
        await dispatch(
          clonePatientChatbotTemplate({ patientId: patientId, templateId: currentChatbot.id! })
        ).unwrap()
        dispatch(getAllPatientsOfTherapist())
      } else {
        await dispatch(cloneChatbotTemplate(currentChatbot.id!)).unwrap()
        dispatch(getCurrentlyLoggedInTherapist())
      }
      openSnackbar(t('chatbot.chatbot_cloned_success'), 'success')
    } catch (err) {
      openSnackbar(handleError(err as AxiosError), 'error')
    } finally {
      closeMenu()
    }
  }

  const handleDelete = async (): Promise<void> => {
    if (!currentChatbot) {
      return
    }
    try {
      if (patientId) {
        await dispatch(
          deletePatientChatbotTemplate({ patientId: patientId, templateId: currentChatbot.id! })
        ).unwrap()
        dispatch(getAllPatientsOfTherapist())
      } else {
        await dispatch(deleteChatbotTemplate(currentChatbot.id!)).unwrap()
        dispatch(getCurrentlyLoggedInTherapist())
      }
      openSnackbar(t('chatbot.chatbot_deleted_success'), 'success')
    } catch (err) {
      openSnackbar(handleError(err as AxiosError), 'error')
    } finally {
      closeMenu()
    }
  }

  const renderCard = (
    bot: ChatbotTemplateOutputDTO,
    { showMenu = true, onClick }: { showMenu?: boolean; onClick?: () => void } = {}
  ): ReactElement => (
    <Card
      key={bot.id}
      sx={{
        maxWidth: 300,
        minWidth: 300,
        maxHeight: 250,
        minHeight: 250,
        display: 'flex',
        flexDirection: 'column',
        border: '1px solid #e0e0e0',
        boxShadow: 'none',
        borderRadius: 2,
      }}
    >
      <CardActionArea
        onClick={
          onClick ??
          ((): void => {
            openTemplate(bot)
          })
        }
        sx={{ height: '100%' }}
      >
        <CardContent
          sx={{
            px: 2,
            pb: 10,
            '&:last-child': { pb: 2 },
          }}
        >
          <Box display='flex' justifyContent='space-between' alignItems='center' sx={{ mb: 1 }}>
            <Typography
              variant='h6'
              sx={{
                pr: 1,
                flexGrow: 1,
                overflow: 'hidden',
                textOverflow: 'ellipsis',
                whiteSpace: 'nowrap',
                m: 0,
              }}
              title={bot.chatbotName || t('dashboard.unnamed_bot')}
            >
              {bot.chatbotName || t('dashboard.unnamed_bot')}
            </Typography>

            <Box display='flex' alignItems='center' gap={1}>
              {bot.patientId &&
                (bot.isActive ? (
                  <Chip size='small' color='success' label='Active' />
                ) : (
                  <Chip size='small' variant='outlined' label='Inactive' />
                ))}

              {showMenu && (
                <IconButton
                  size='small'
                  onClick={(e) => {
                    e.stopPropagation()
                    handleMenu(e, bot)
                  }}
                >
                  <MoreVertIcon fontSize='small' />
                </IconButton>
              )}
            </Box>
          </Box>

          <Typography variant='body1' sx={{ mt: 0.25 }}>
            {t('dashboard.role')}: {bot.chatbotRole}
          </Typography>
          <Typography variant='body1'>{`Tone: ${bot.chatbotTone}`}</Typography>

          <Box
            sx={{
              fontSize: 48,
              textAlign: 'center',
              mt: 2,
              lineHeight: 1,
            }}
          >
            {iconFor(bot.chatbotIcon ?? '')}
          </Box>
        </CardContent>
      </CardActionArea>
    </Card>
  )

  return (
    <>
      <Box sx={{ display: 'flex', gap: 2, alignItems: 'center', mb: 2 }}>
        <Typography variant='h2'>{t('chatbot.chatbots')}</Typography>
        <Button sx={{ ...commonButtonStyles, minWidth: '200px' }} onClick={handleCreateChatbot}>
          {t('chatbot.create_new_chatbot')}
        </Button>
      </Box>

      {patientId ? (
        <>
          {therapistTemplates.length > 0 && (
            <>
              <Typography variant='h4' sx={{ mb: 3 }}>
                {t('chatbot.create_chatbot_from_template')}
              </Typography>
              <Box sx={{ display: 'flex', flexWrap: 'wrap', gap: 2, mb: 3 }}>
                {therapistTemplates.map((bot) =>
                  renderCard(bot, {
                    showMenu: false,
                    onClick: () => handleCreateFromTherapistTemplate(bot),
                  })
                )}
              </Box>
            </>
          )}

          {patientTemplates.length ? (
            <>
              <Box sx={{ display: 'flex', alignItems: 'center', gap: 2, mb: 3 }}>
                <Typography variant='h4'>{t('chatbot.client_chatbot')}</Typography>

                <Button
                  size='small'
                  sx={{ ...commonButtonStyles, minWidth: '220px' }}
                  onClick={() =>
                    navigate(
                      getPathFromPage(PAGES.PATIENT_CONVERSATIONS_PAGE, { patientId: patientId! }),
                      { state: { patientId: patientId } }
                    )
                  }
                >
                  {t('chatbot.conversation_summary')}
                </Button>
              </Box>

              <Box sx={{ display: 'flex', flexWrap: 'wrap', gap: 2 }}>
                {patientTemplates.map((bot) => renderCard(bot, { showMenu: true }))}
              </Box>
            </>
          ) : (
            <Box>
              <Typography variant='h4' sx={{ mb: 1 }}>
                {t('chatbot.client_chatbots')}
              </Typography>
              <Typography sx={{ mt: 2 }}>{t('chatbot.no_chatbots_created_yet')}</Typography>
            </Box>
          )}
        </>
      ) : (
        <>
          {therapistTemplates.length ? (
            <Box sx={{ display: 'flex', flexWrap: 'wrap', gap: 2 }}>
              {therapistTemplates.map((bot) => renderCard(bot, { showMenu: true }))}
            </Box>
          ) : (
            <Typography>{t('chatbot.no_chatbots_created_yet')}</Typography>
          )}
        </>
      )}

      <Menu anchorEl={anchorEl} open={Boolean(anchorEl)} onClose={closeMenu}>
        <MenuItem onClick={handleClone}>{t('dashboard.clone')}</MenuItem>

        {onlyOnePatientTemplateLeft && currentChatbot?.id === patientTemplates[0]?.id ? (
          <Tooltip title='Cannot delete the last template'>
            <span>
              <MenuItem disabled>{t('dashboard.delete')}</MenuItem>
            </span>
          </Tooltip>
        ) : (
          <MenuItem onClick={handleDelete}>{t('dashboard.delete')}</MenuItem>
        )}
      </Menu>

      <Snackbar
        open={snackbar.open}
        autoHideDuration={6000}
        onClose={() => setSnackbar({ ...snackbar, open: false })}
        anchorOrigin={{ vertical: 'bottom', horizontal: 'center' }}
      >
        <Alert
          onClose={() => setSnackbar({ ...snackbar, open: false })}
          severity={snackbar.severity}
          sx={{ width: '100%' }}
        >
          {snackbar.message}
        </Alert>
      </Snackbar>
    </>
  )
}

export default ChatbotOverview
