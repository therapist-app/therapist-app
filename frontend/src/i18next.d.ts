import "i18next";
import type {Translations} from './translations';

declare module "i18next" {
    interface CustomTypeOptions {
        returnNull: false; // Ensure fallback if translation is missing
        resources: {
            translation: Translations; // Use the generated types
        };
    }
}
