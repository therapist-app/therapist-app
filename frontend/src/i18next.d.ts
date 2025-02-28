import "i18next";
import type translation from '../public/locales/en.json';

declare module "i18next" {
    interface CustomTypeOptions {
        returnNull: false; // Ensure fallback if translation is missing
        resources: {
            translation: typeof translation; // Use the generated types
        };
    }
}
