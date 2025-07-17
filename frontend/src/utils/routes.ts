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

  NOT_FOUND_PAGE = 'NOT_FOUND_PAGE',
}

const PAGE_HIERARCHY: Record<PAGES, PAGES[]> = {
  [PAGES.HOME_PAGE]: [
    PAGES.PATIENTS_CREATE_PAGE,
    PAGES.PATIENTS_DETAILS_PAGE,
    PAGES.CHATBOT_TEMPLATES_CREATE_PAGE,
    PAGES.CHATBOT_TEMPLATES_DETAILS_PAGE,
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
    PAGES.COUNSELING_PLAN_DETAILS_PAGE,
    PAGES.GAD7_TEST_CREATE_PAGE,
    PAGES.PATIENT_CONVERSATIONS_PAGE,
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

  [PAGES.NOT_FOUND_PAGE]: [],
}

export const ROUTES: Record<PAGES, string> = {
  [PAGES.HOME_PAGE]: '/',
  [PAGES.LOGIN_PAGE]: '/login',
  [PAGES.REGISTRATION_PAGE]: '/register',
  [PAGES.SETTINGS_PAGE]: '/settings',

  [PAGES.PATIENTS_CREATE_PAGE]: '/patients/create',
  [PAGES.PATIENTS_DETAILS_PAGE]: '/patients/:patientId',

  [PAGES.PATIENT_CONVERSATIONS_PAGE]: '/patients/:patientId/conversations-summary',

  [PAGES.CHATBOT_CREATE_PAGE]: '/patients/:patientId/chatBots/create',
  [PAGES.CHATBOT_DETAILS_PAGE]: '/patients/:patientId/chatBots/:chatBotId',

  [PAGES.MEETINGS_CREATE_PAGE]: '/patients/:patientId/meetings/create',
  [PAGES.MEETINGS_DETAILS_PAGE]: '/patients/:patientId/meetings/:meetingId',

  [PAGES.GAD7_TEST_CREATE_PAGE]: '/patients/:patientId/gad7',

  [PAGES.EXERCISES_CREATE_PAGE]: '/patients/:patientId/exercises/create',
  [PAGES.EXERCISES_DETAILS_PAGE]: '/patients/:patientId/exercises/:exerciseId',

  [PAGES.CHATBOT_TEMPLATES_CREATE_PAGE]: '/chatBotTemplates/create',
  [PAGES.CHATBOT_TEMPLATES_DETAILS_PAGE]: '/chatBotTemplates/:chatbotTemplateId',

  [PAGES.COUNSELING_PLAN_DETAILS_PAGE]: '/patients/:patientId/counselingPlan',

  [PAGES.NOT_FOUND_PAGE]: '*',
}

export const PAGE_NAMES: Record<PAGES, string> = {
  [PAGES.HOME_PAGE]: 'pages.home',
  [PAGES.LOGIN_PAGE]: 'pages.login',
  [PAGES.REGISTRATION_PAGE]: 'pages.registration',
  [PAGES.SETTINGS_PAGE]: 'pages.settings',

  [PAGES.PATIENTS_CREATE_PAGE]: 'pages.patients.create',
  [PAGES.PATIENTS_DETAILS_PAGE]: 'pages.patients.details',

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
