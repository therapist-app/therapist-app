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

  GAD7_TEST_PAGE = 'GAD7_TEST_PAGE',

  CHATBOT_TEMPLATES_OVERVIEW_PAGE = 'CHATBOT_TEMPLATES_OVERVIEW_PAGE',
  CHATBOT_TEMPLATES_CREATE_PAGE = 'CHATBOT_TEMPLATES_CREATE_PAGE',
  CHATBOT_TEMPLATES_DETAILS_PAGE = 'CHATBOT_TEMPLATES_DETAILS_PAGE',

  NOT_FOUND_PAGE = 'NOT_FOUND_PAGE',
}

const PAGE_HIERARCHY: Record<PAGES, PAGES[]> = {
  [PAGES.HOME_PAGE]: [PAGES.PATIENTS_OVERVIEW_PAGE, PAGES.CHATBOT_TEMPLATES_OVERVIEW_PAGE],
  [PAGES.LOGIN_PAGE]: [],
  [PAGES.REGISTRATION_PAGE]: [],
  [PAGES.SETTINGS_PAGE]: [],

  [PAGES.PATIENTS_OVERVIEW_PAGE]: [PAGES.PATIENTS_CREATE_PAGE, PAGES.PATIENTS_DETAILS_PAGE],
  [PAGES.PATIENTS_CREATE_PAGE]: [],
  [PAGES.PATIENTS_DETAILS_PAGE]: [
    PAGES.CHATBOT_OVERVIEW_PAGE,
    PAGES.THERAPY_SESSIONS_OVERVIEW_PAGE,
  ],

  [PAGES.CHATBOT_OVERVIEW_PAGE]: [PAGES.CHATBOT_CREATE_PAGE, PAGES.CHATBOT_DETAILS_PAGE],
  [PAGES.CHATBOT_CREATE_PAGE]: [],
  [PAGES.CHATBOT_DETAILS_PAGE]: [],

  [PAGES.THERAPY_SESSIONS_OVERVIEW_PAGE]: [
    PAGES.THERAPY_SESSIONS_CREATE_PAGE,
    PAGES.THERAPY_SESSIONS_DETAILS_PAGE,
  ],
  [PAGES.THERAPY_SESSIONS_CREATE_PAGE]: [],
  [PAGES.THERAPY_SESSIONS_DETAILS_PAGE]: [PAGES.GAD7_TEST_PAGE],
  [PAGES.GAD7_TEST_PAGE]: [],

  [PAGES.CHATBOT_TEMPLATES_OVERVIEW_PAGE]: [
    PAGES.CHATBOT_TEMPLATES_CREATE_PAGE,
    PAGES.CHATBOT_TEMPLATES_DETAILS_PAGE,
  ],
  [PAGES.CHATBOT_TEMPLATES_CREATE_PAGE]: [],
  [PAGES.CHATBOT_TEMPLATES_DETAILS_PAGE]: [],

  [PAGES.NOT_FOUND_PAGE]: [],
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
  [PAGES.GAD7_TEST_PAGE]: '/patients/:patientId/therapy-sessions/:therapySessionId/gad7',

  [PAGES.CHATBOT_TEMPLATES_OVERVIEW_PAGE]: '/chatBotTemplates',
  [PAGES.CHATBOT_TEMPLATES_CREATE_PAGE]: '/chatBotTemplates/create',
  [PAGES.CHATBOT_TEMPLATES_DETAILS_PAGE]: '/chatBotTemplates/:chatbotTemplateId',

  [PAGES.NOT_FOUND_PAGE]: '*',
}

export const PAGE_NAMES: Record<PAGES, string> = {
  [PAGES.HOME_PAGE]: 'Home',
  [PAGES.LOGIN_PAGE]: 'Login',
  [PAGES.REGISTRATION_PAGE]: 'Registration',
  [PAGES.SETTINGS_PAGE]: 'Settings',

  [PAGES.PATIENTS_OVERVIEW_PAGE]: 'All Patients',
  [PAGES.PATIENTS_CREATE_PAGE]: 'Create new Patient',
  [PAGES.PATIENTS_DETAILS_PAGE]: 'Patient Details',

  [PAGES.CHATBOT_OVERVIEW_PAGE]: 'All Chatbots',
  [PAGES.CHATBOT_CREATE_PAGE]: 'Create new Chatbot',
  [PAGES.CHATBOT_DETAILS_PAGE]: 'Chatbot Details',

  [PAGES.THERAPY_SESSIONS_OVERVIEW_PAGE]: 'All Sessions',
  [PAGES.THERAPY_SESSIONS_CREATE_PAGE]: 'Create new Session',
  [PAGES.THERAPY_SESSIONS_DETAILS_PAGE]: 'Session Details',
  [PAGES.GAD7_TEST_PAGE]: 'GAD-7 Assessment',

  [PAGES.CHATBOT_TEMPLATES_OVERVIEW_PAGE]: 'All Chatbot Templates',
  [PAGES.CHATBOT_TEMPLATES_CREATE_PAGE]: 'Create new Chatbot Template',
  [PAGES.CHATBOT_TEMPLATES_DETAILS_PAGE]: 'Chatbot Template Details',

  [PAGES.NOT_FOUND_PAGE]: 'Not Found',
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

export function findPageTrace(
  targetPage: PAGES,
  currentPage: PAGES = PAGES.HOME_PAGE,
  visited = new Set<PAGES>()
): PAGES[] | null {
  if (visited.has(currentPage)) {
    return null
  }
  visited.add(currentPage)

  if (currentPage === targetPage) {
    return [currentPage]
  }

  const children = PAGE_HIERARCHY[currentPage] || []
  for (const child of children) {
    const result = findPageTrace(targetPage, child, visited)
    if (result) {
      return [currentPage, ...result]
    }
  }

  return null
}
