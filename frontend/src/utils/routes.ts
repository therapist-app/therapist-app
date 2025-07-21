import { compile, match } from 'path-to-regexp'

export enum PAGES {
  HOME_PAGE = 'HOME_PAGE',
  LOGIN_PAGE = 'LOGIN_PAGE',
  REGISTRATION_PAGE = 'REGISTRATION_PAGE',
  SETTINGS_PAGE = 'SETTINGS_PAGE',

  PATIENTS_CREATE_PAGE = 'PATIENTS_CREATE_PAGE',
  PATIENTS_DETAILS_PAGE = 'PATIENTS_DETAILS_PAGE',

  CHATBOT_CREATE_PAGE = 'CHATBOT_CREATE_PAGE',
  CHATBOT_DETAILS_PAGE = 'CHATBOT_DETAILS_PAGE',

  PATIENT_CONVERSATIONS_PAGE = 'PATIENT_CONVERSATIONS_PAGE',

  MEETINGS_CREATE_PAGE = 'MEETINGS_CREATE_PAGE',
  MEETINGS_DETAILS_PAGE = 'MEETINGS_DETAILS_PAGE',

  GAD7_TEST_CREATE_PAGE = 'GAD7_TEST_CREATE_PAGE',

  EXERCISES_CREATE_PAGE = 'EXERCISES_CREATE_PAGE',
  EXERCISES_DETAILS_PAGE = 'EXERCISES_DETAILS_PAGE',

  CHATBOT_TEMPLATES_CREATE_PAGE = 'CHATBOT_TEMPLATES_CREATE_PAGE',
  CHATBOT_TEMPLATES_DETAILS_PAGE = 'CHATBOT_TEMPLATES_DETAILS_PAGE',

  COUNSELING_PLAN_DETAILS_PAGE = 'COUNSELING_PLAN_DETAILS_PAGE',

  CLIENT_INTERACTIONS_PAGE = 'CLIENT_INTERACTIONS_PAGE',

  THERAPIST_CHATBOT_PAGE = 'THERAPIST_CHATBOT_PAGE',
  THERAPIST_CHATBOT_PAGE_BY_PATIENT = 'THERAPIST_CHATBOT_PAGE_BY_PATIENT',

  NOT_FOUND_PAGE = 'NOT_FOUND_PAGE',
}

const PAGE_HIERARCHY: Record<PAGES, PAGES[]> = {
  [PAGES.HOME_PAGE]: [
    PAGES.SETTINGS_PAGE,
    PAGES.PATIENTS_CREATE_PAGE,
    PAGES.PATIENTS_DETAILS_PAGE,
    PAGES.CHATBOT_TEMPLATES_CREATE_PAGE,
    PAGES.CHATBOT_TEMPLATES_DETAILS_PAGE,
    PAGES.THERAPIST_CHATBOT_PAGE,
  ],
  [PAGES.LOGIN_PAGE]: [],
  [PAGES.REGISTRATION_PAGE]: [],
  [PAGES.SETTINGS_PAGE]: [],

  [PAGES.PATIENTS_CREATE_PAGE]: [],
  [PAGES.PATIENTS_DETAILS_PAGE]: [
    PAGES.CHATBOT_CREATE_PAGE,
    PAGES.CHATBOT_DETAILS_PAGE,
    PAGES.MEETINGS_CREATE_PAGE,
    PAGES.MEETINGS_DETAILS_PAGE,
    PAGES.EXERCISES_CREATE_PAGE,
    PAGES.EXERCISES_DETAILS_PAGE,
    PAGES.CLIENT_INTERACTIONS_PAGE,
    PAGES.COUNSELING_PLAN_DETAILS_PAGE,
    PAGES.GAD7_TEST_CREATE_PAGE,
    PAGES.PATIENT_CONVERSATIONS_PAGE,
    PAGES.THERAPIST_CHATBOT_PAGE_BY_PATIENT,
  ],

  [PAGES.CHATBOT_CREATE_PAGE]: [],
  [PAGES.CHATBOT_DETAILS_PAGE]: [],

  [PAGES.MEETINGS_CREATE_PAGE]: [],
  [PAGES.MEETINGS_DETAILS_PAGE]: [],

  [PAGES.GAD7_TEST_CREATE_PAGE]: [],

  [PAGES.EXERCISES_CREATE_PAGE]: [],
  [PAGES.EXERCISES_DETAILS_PAGE]: [],

  [PAGES.CHATBOT_TEMPLATES_CREATE_PAGE]: [],
  [PAGES.CHATBOT_TEMPLATES_DETAILS_PAGE]: [],

  [PAGES.COUNSELING_PLAN_DETAILS_PAGE]: [],

  [PAGES.PATIENT_CONVERSATIONS_PAGE]: [],

  [PAGES.CLIENT_INTERACTIONS_PAGE]: [],

  [PAGES.NOT_FOUND_PAGE]: [],

  [PAGES.THERAPIST_CHATBOT_PAGE]: [],
  [PAGES.THERAPIST_CHATBOT_PAGE_BY_PATIENT]: [],
}

export const ROUTES: Record<PAGES, string> = {
  [PAGES.HOME_PAGE]: '/',
  [PAGES.LOGIN_PAGE]: '/login',
  [PAGES.REGISTRATION_PAGE]: '/register',
  [PAGES.SETTINGS_PAGE]: '/settings',

  [PAGES.PATIENTS_CREATE_PAGE]: '/clients/create',
  [PAGES.PATIENTS_DETAILS_PAGE]: '/clients/:patientId',

  [PAGES.CLIENT_INTERACTIONS_PAGE]: '/clients/:patientId/interactions',
  [PAGES.PATIENT_CONVERSATIONS_PAGE]: '/clients/:patientId/conversations-summary',

  [PAGES.CHATBOT_CREATE_PAGE]: '/clients/:patientId/chatBots/create',
  [PAGES.CHATBOT_DETAILS_PAGE]: '/clients/:patientId/chatBots/:chatBotId',

  [PAGES.MEETINGS_CREATE_PAGE]: '/clients/:patientId/meetings/create',
  [PAGES.MEETINGS_DETAILS_PAGE]: '/clients/:patientId/meetings/:meetingId',

  [PAGES.GAD7_TEST_CREATE_PAGE]: '/clients/:patientId/gad7',

  [PAGES.EXERCISES_CREATE_PAGE]: '/clients/:patientId/exercises/create',
  [PAGES.EXERCISES_DETAILS_PAGE]: '/clients/:patientId/exercises/:exerciseId',

  [PAGES.CHATBOT_TEMPLATES_CREATE_PAGE]: '/chatBotTemplates/create',
  [PAGES.CHATBOT_TEMPLATES_DETAILS_PAGE]: '/chatBotTemplates/:chatbotTemplateId',

  [PAGES.COUNSELING_PLAN_DETAILS_PAGE]: '/clients/:patientId/counselingPlan',

  [PAGES.THERAPIST_CHATBOT_PAGE]: '/coach-chatbot',
  [PAGES.THERAPIST_CHATBOT_PAGE_BY_PATIENT]: '/clients/:patientId/coach-chatbot',

  [PAGES.NOT_FOUND_PAGE]: '*',
}

export const PAGE_NAMES: Record<PAGES, string> = {
  [PAGES.HOME_PAGE]: 'pages.home',
  [PAGES.LOGIN_PAGE]: 'pages.login',
  [PAGES.REGISTRATION_PAGE]: 'pages.registration',
  [PAGES.SETTINGS_PAGE]: 'pages.settings',

  [PAGES.PATIENTS_CREATE_PAGE]: 'pages.patients.create',
  [PAGES.PATIENTS_DETAILS_PAGE]: 'pages.patients.details',

  [PAGES.CLIENT_INTERACTIONS_PAGE]: 'pages.patients.interactions',
  [PAGES.PATIENT_CONVERSATIONS_PAGE]: 'pages.patients.conversations',

  [PAGES.CHATBOT_CREATE_PAGE]: 'pages.chatbot.create',
  [PAGES.CHATBOT_DETAILS_PAGE]: 'pages.chatbot.details',

  [PAGES.MEETINGS_CREATE_PAGE]: 'pages.meetings.create',
  [PAGES.MEETINGS_DETAILS_PAGE]: 'pages.meetings.details',

  [PAGES.GAD7_TEST_CREATE_PAGE]: 'pages.gad7.create',

  [PAGES.EXERCISES_CREATE_PAGE]: 'pages.exercises.create',
  [PAGES.EXERCISES_DETAILS_PAGE]: 'pages.exercises.details',

  [PAGES.CHATBOT_TEMPLATES_CREATE_PAGE]: 'pages.chatbot_templates.create',
  [PAGES.CHATBOT_TEMPLATES_DETAILS_PAGE]: 'pages.chatbot_templates.details',

  [PAGES.COUNSELING_PLAN_DETAILS_PAGE]: 'pages.counseling_plan.details',
  [PAGES.THERAPIST_CHATBOT_PAGE]: 'pages.therapist_chatbot',
  [PAGES.THERAPIST_CHATBOT_PAGE_BY_PATIENT]: 'pages.therapist_chatbot',

  [PAGES.NOT_FOUND_PAGE]: 'pages.not_found',
}

export const getPageName = (page: PAGES, t: (key: string) => string): string => {
  return t(PAGE_NAMES[page])
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
  // Defensive check for the 'page' argument itself.
  if (!page || !ROUTES[page]) {
    console.error(`[Routing Error] Invalid page passed to getPathFromPage: ${page}`)
    return ROUTES[PAGES.NOT_FOUND_PAGE] || '*'
  }

  const routePattern = ROUTES[page]

  if (routePattern === '*') {
    return '*'
  }

  try {
    const toPath = compile(routePattern, { encode: encodeURIComponent })
    return toPath(params)
  } catch (error) {
    // This will catch the error if compile fails on a specific pattern.
    console.error(
      `[Routing Error] Failed to generate path for page ${page} with pattern "${routePattern}":`,
      error
    )
    return ROUTES[PAGES.NOT_FOUND_PAGE] || '*'
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
