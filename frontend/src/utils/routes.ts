import { compile, match } from 'path-to-regexp'

export enum PAGES {
  HOME_PAGE = 'HOME_PAGE',
  LOGIN_PAGE = 'LOGIN_PAGE',
  REGISTRATION_PAGE = 'REGISTRATION_PAGE',
  SETTINGS_PAGE = 'SETTINGS_PAGE',

  PATIENTS_OVERVIEW_PAGE = 'PATIENTS_OVERVIEW_PAGE',
  PATIENTS_CREATE_PAGE = 'PATIENTS_CREATE_PAGE',
  PATIENTS_DETAILS_PAGE = 'PATIENTS_DETAILS_PAGE',

  CHATBOT_OVERVIEW_PAGE = 'CHATBOT_OVERVIEW_PAGE',
  CHATBOT_CREATE_PAGE = 'CHATBOT_CREATE_PAGE',
  CHATBOT_DETAILS_PAGE = 'CHATBOT_DETAILS_PAGE',

  THERAPY_SESSIONS_OVERVIEW_PAGE = 'THERAPY_SESSIONS_OVERVIEW_PAGE',
  THERAPY_SESSIONS_CREATE_PAGE = 'THERAPY_SESSIONS_CREATE_PAGE',
  THERAPY_SESSIONS_DETAILS_PAGE = 'THERAPY_SESSIONS_DETAILS_PAGE',

  CHATBOT_TEMPLATES_OVERVIEW_PAGE = 'CHATBOT_TEMPLATES_OVERVIEW_PAGE',
  CHATBOT_TEMPLATES_CREATE_PAGE = 'CHATBOT_TEMPLATES_CREATE_PAGE',
  CHATBOT_TEMPLATES_DETAILS_PAGE = 'CHATBOT_TEMPLATES_DETAILS_PAGE',

  NOT_FOUND_PAGE = 'NOT_FOUND_PAGE',
}

export const ROUTES: Record<PAGES, string> = {
  [PAGES.HOME_PAGE]: '/',
  [PAGES.LOGIN_PAGE]: '/login',
  [PAGES.REGISTRATION_PAGE]: '/register',
  [PAGES.SETTINGS_PAGE]: '/settings',

  [PAGES.PATIENTS_OVERVIEW_PAGE]: '/patients',
  [PAGES.PATIENTS_CREATE_PAGE]: '/patients/create',
  [PAGES.PATIENTS_DETAILS_PAGE]: '/patients/:patientId',

  [PAGES.CHATBOT_OVERVIEW_PAGE]: '/patients/:patientId/chatBots',
  [PAGES.CHATBOT_CREATE_PAGE]: '/patients/:patientId/chatBots/create',
  [PAGES.CHATBOT_DETAILS_PAGE]: '/patients/:patientId/chatBots/:chatBotId',

  [PAGES.THERAPY_SESSIONS_OVERVIEW_PAGE]: '/patients/:patientId/therapy-sessions',
  [PAGES.THERAPY_SESSIONS_CREATE_PAGE]: '/patients/:patientId/therapy-sessions/create',
  [PAGES.THERAPY_SESSIONS_DETAILS_PAGE]: '/patients/:patientId/therapy-sessions/:therapySessionId',

  [PAGES.CHATBOT_TEMPLATES_OVERVIEW_PAGE]: '/chatBotTemplates',
  [PAGES.CHATBOT_TEMPLATES_CREATE_PAGE]: '/chatBotTemplates/create',
  [PAGES.CHATBOT_TEMPLATES_DETAILS_PAGE]: '/chatBotTemplates/:chatbotTemplateId',

  [PAGES.NOT_FOUND_PAGE]: '*',
}

export function getPageFromPath(pathname: string): PAGES {
  for (const [page, routePattern] of Object.entries(ROUTES)) {
    const matcher = match(routePattern, { decode: decodeURIComponent })
    if (matcher(pathname)) {
      return page as PAGES
    }
  }

  return PAGES.NOT_FOUND_PAGE
}

export function getPathFromPage(page: PAGES, params: Record<string, string> = {}): string {
  const routePattern = ROUTES[page]

  if (!routePattern || routePattern === '*') {
    return ROUTES[PAGES.NOT_FOUND_PAGE]
  }

  try {
    const toPath = compile(routePattern, { encode: encodeURIComponent })
    return toPath(params)
  } catch (error) {
    console.error(`Failed to generate path for page ${page}`, error)
    return ROUTES[PAGES.NOT_FOUND_PAGE]
  }
}
