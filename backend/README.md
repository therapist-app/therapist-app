# No-Code-Plattform zur Entwicklung von pädagogischen Sprachagenten

Willkommen im GitHub-Repository der No-Code-Plattform zur Erstellung von pädagogischen Sprachagenten. Diese Plattform ermöglicht Mitarbeiter aus Fachabteilungen ohne Programmierkenntnisse, insbesonderene im Bildungsbereich, eigene Chatbots zu entwickeln. Die No-Code-Umgebung ist Teil einer Bachelorarbeit und hat das Ziel die Lücke zwischen technischer Innovation und praktischer Anwendbarkeit im Bildungsbereich zu schliessen.

## Projektübersicht

Die No-Code-Plattform ermöglicht es sogennanten Citizen Developern, ohne technische Vorkenntnisse individuelle pädagogische Sprachagenten zu entwickeln. Nach dem Erstellen können die Chatbots mittels iFrame schnell und effizient in bestehende Webapplikationen integriert werden.

### Funktionen

**Registrierungsfunktion**  
![Registrierungsfunktion](https://github.com/user-attachments/assets/7a163a46-33a3-4ede-9a13-cacd5194d9d8)

**Anmeldefunktion**  
![Anmeldefunktion](https://github.com/user-attachments/assets/b2012a2d-9a81-4ca1-9a09-40d395e0d489)


**Dashboard-Funktion**  
![Dashboard-Funktion](https://github.com/user-attachments/assets/4f2e6104-7205-4682-b7c0-4cadfc0bf346)


**Eingabefunktion für Chatbot-Namen**  
![Eingabefunktion für Chatbot-Namen](https://github.com/user-attachments/assets/b06f740a-aff4-437f-81e2-4ce75fd32e1d)


**Chatbot-Konfigurationsfunktion**  
![Chatbot-Konfigurationsfunktion](https://github.com/user-attachments/assets/b1779864-6f89-461d-a119-e00f73d391d5)

**iFrame-Funktion**  
![iFrame-Funktion](https://github.com/user-attachments/assets/d47b6757-bc81-4127-b446-f21fc0faf376)


**Analysefunktion**  
![Analysefunktion](https://github.com/user-attachments/assets/8c3a4305-921b-4fab-98c7-a53deecc46ba)


**Ressourcen-Funktion**  
![Ressourcen-Funktion](https://github.com/user-attachments/assets/e5c23e71-2f94-4be6-a416-4a7c3ea0aa66)


**Automatische Benachrichtigungsfunktion**  
![Automatische Benachrichtigungsfunktion](https://github.com/user-attachments/assets/4d66d817-f3d8-4818-86f1-8daec8627b63)


## Erste Schritte

### Voraussetzungen

- Keine Programmierkenntnisse erforderlich.
- Zugang zu einem modernen Webbrowser.

### Installation

1. **Klonen des Repositories**: `git clone git@github.com:no-code-platform-chatbot/no-code-platform-chatbot-server.git`
2. **Navigieren ins Projektverzeichnis**: `cd no-code-platform-chatbot-server`
3. **Lokales Starten der Applikation**: `Unter application.properties "${sm://openai_api_key}" durch den OpenAI API-Key ersetzen. Unter build.gradle "implementation 'com.google.cloud:spring-cloud-gcp-starter-secretmanager:3.4.0'" löschen und die Applikation mit ./gradlew bootRun starten.`

## Support

Bei Fragen oder Unklarheiten besuchen Sie bitte den Bereich [Issues](https://github.com/no-code-platform-chatbot/no-code-platform-chatbot-server/issues) dieses GitHub-Repositories.

## Lizenz

Dieses Projekt ist unter der MIT-Lizenz lizenziert - Details finden Sie in der Datei [LICENSE](LICENSE).
