import i18n from "i18next";
import {initReactI18next} from "react-i18next";
import HttpApi from "i18next-http-backend";
import LanguageDetector from "i18next-browser-languagedetector";

i18n
    .use(HttpApi)
    .use(LanguageDetector) // Detect user language
    .use(initReactI18next) // Bind i18next to react-i18next
    .init({
        supportedLngs: ["en", "ua"],
        fallbackLng: "en",
        debug: true, // Set to false in production
        interpolation: {
            escapeValue: false,
        },
        backend: {
            loadPath: "/locales/{{lng}}.json", // Translation files path
        },
        detection: {
            order: ["localStorage", "cookie", "navigator"], // Detect language from user settings
            caches: ["localStorage", "cookie"],
        },
    }).then(() => {}).catch((err) => {
    console.error('i18n initialization failed', err);
});

export default i18n;
