# Therapist App

## How to run Backend

1. Install openjdk-21, when typing `java --version` in the terminal the output should be `openjdk 21 2023-09-19`
2. Install docker and docker compose
3. Copy the `backend/src/main/resources/application-dev.properties.example` file to `backend/src/main/resources/application-dev.properties` and adjust the values if needed
4. `cd backend`
5. `docker compose up -d` or (`docker-compose up -d`)
6. Visit <http://localhost:5050> in your browser to check if the database is running (email: <admin@admin.com>, password: admin)
7. On <http://localhost:5050> connect to DB -> right click "Servers" -> "Register Server" -> "name" = `therapy`, "Host name/address" = `therapy-postgres`, "Port" = `5432`, "Username" = `therapy-user`, "Password" = `therapy-password`, and "Save Password?" = `Yes`
8. `./gradlew bootRun`
9. Visit <http://localhost:8080/coach-backend> -> it should say: "The application is running."

## How to run Frontend

1. Install nodejs 22.14.0 -> when typing `node -v` in the terminal, the output should be `v22.14.0`
2. `cd frontend`
3. `npm install`
4. `npm run dev`
5. Visit <http://localhost:5173/coach/register> and register as a new therapist

## Environment Variables

###  Frontend

- In the frontend there are ususally no secrets (as all of the code is sent to the client anyways)
- To add a new environment variable, add it to the `.env` file in the frontend folder and to the `.env.production.main` and `.env.production.production` files
- When building the docker image, the environment variables from the `.env.production.main` and `.env.production.production` files are automatically added to the built image

### Backend

###  Non-Secret Environment Variables

1. To add a new non-secret environment variable, add it to the `application-dev.properties.example`, `application-dev.properties` and `application-prod.properties` files in the backend folder.
2. Additionally add the .env variables to the `.kubernetes/overlays/main/backend/kustomization.yaml` and `.kubernetes/overlays/production/backend/kustomization.yaml` files under the configMapGenerator section (for the main and production environments)
3. Add the new environment variable to the `backend/src/main/java/ch/uzh/ifi/imrg/platform/utils/EnvironmentVariables.java` file so it can be easily accessed in the code

###  Secret Environment Variables

1. To add a new non-secret environment variable, add it to the `application-dev.properties.example`, `application-dev.properties` and `application-prod.properties` files in the backend folder.
2. Additionally add the .env variable as a repository secret in Github under `Settings` -> `Secrets and variables` -> `New repository secret`. Add it for both main and production environments. E.g. `DB_PASSWORD_MAIN` and `DB_PASSWORD_PRODUCTION`
3. In the `.github/workflows/deploy.yml` file under the `Restart Kubernetes Deployments` section add the new secret to env section and echo it to the kubernetes overlays file in the main and production environments (see current implementation for reference)
4. Add the new environment variable to the `backend/src/main/java/ch/uzh/ifi/imrg/platform/utils/EnvironmentVariables.java` file so it can be easily accessed in the code

## Main and Production Environments

###  Main Environment

- The "main" environment shows the latest changes on the main branch
- Frontend: <https://therapist-app-main.jonas-blum.ch/coach>
- Backend: <https://backend-therapist-app-main.jonas-blum.ch/coach>

### Production Environment

- The "production" environment shows the latest changes on the production branch
- Frontend: <https://therapist-app-production.jonas-blum.ch/coach>
- Backend: <https://backend-therapist-app-production.jonas-blum.ch/coach>

## Pre-Commit Hooks

- Automatically formats your code before every commit
- on Github actions the code formatting is also checked -> so either you need to do it manually or automatically whenever you are committing code

1. `cd frontend`
2. `npm install`
3. `npm run prepare`
4. Now whenver you commit code in the frontend the code will be formatted automatically

### Running Code formatting manually (not needed if you have pre-commit hooks setup)

1. `cd frontend`
2. `npm run fix-all`
3. `cd ..`
4. `cd backend`
5. `./gradlew spotlessApply`

## Workflow: How to implement an issue

1. Look at the issue number and create a new branch (from main) with the name `issueNumber-issue-title` (e.g. `5-create-login-register-endpoint`)
2. Do some changes and add your first commit
3. As soon as you added the first commit, push the branch (so other team member are aware of your work and can already see if any problems might arise -> if everyone just codes by themselves the collaboration is usually a lot worse)
4. Create a pull request from your branch to main
5. Add the issue number to the pull request title (e.g. `5: Create login/register endpoint`)
6. In the description of the pull request, add `-closes #5` to automatically close the issue when the pull request is merged
7. Assign the pull request to yourself
8. When you are done with the implementation do the file formatting for the frontend/backend wherever you worked on (formatting is applied automatically if you have the pre-commit hooks setup):

   - For the frontend (inside the /frontend folder): `npm run fix-all`
   - For the backend (inside the /backend folder): `./gradlew spotlessApply`

9. After applying the file formatting take a look at the changes of the pull request in Github under "Files changed" to see that everything is correct
10. If everything is correct, merge the pull request with the option "Squash and merge" (so we have a nice history with one commit per issue -> otherwise the commit history is bloated with commits)
11. (Optional) If you cannot merge your branch into main due to a conflict do the following steps:

- `git checkout main`
- `git pull` (or `git reset --hard origin/main` if you have some local changes)
- `git checkout YOUR-BRANCH` (e.g. `5-create-login-register-endpoint`)
- `git rebase -i main`
- Solve the conflicts with the help of your IDE
- Do a force push of your branch `git push -f`
- Now the conflicts should be solved and you can merge your branch into main through Github (with the option "Squash and merge")

## How to update the production branch

1. `git checkout main`
2. `git pull` (or `git reset --hard origin/main` if you have some local changes)
3. `git push --force origin main:production`
