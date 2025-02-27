# Therapist App

## Backend

### How to run

1. Install openjdk-21, when typing `java --version` in the terminal the output should be `openjdk 21 2023-09-19`
2. Install docker and docker compose
3. `cd backend`
4. `docker compose up -d` or (`docker-compose up -d`)
5. Visit `http://localhost:5050` in your browser to check if the database is running (email: admin@admin.com, password: admin)
6. On `http://localhost:5050` connect to DB -> right click "Servers" -> "Register Server" -> "name" = `therapy`, "Host name/address" = `therapy-postgres`, "Port" = `5432`, "Username" = `therapy-user`, "Password" = `therapy-password`, and "Save Password?" = `Yes`
7. `./gradlew bootRun`
8. Visit `http://localhost:8080/therapists` -> you should see a list with three therapists

## Frontend

### How to run

1. Install nodejs 22.14.0 -> when typing `node -v` in the terminal, the output should be `v22.14.0`
2. `cd frontend`
3. `npm install`
4. `npm run dev`
5. Visit `http://localhost:5173` in your browser, you should see a list with three therapists
6. Go to the register page (`http://localhost:5173/register`) and try creating a new therapist

## Workflow: How to implement an issue

1. Look at the issue number and create a new branch (from main) with the name `issueNumber-issue-title` (e.g. `5-create-login-register-endpoint`)
2. Do some changes and add your first commit
3. As soon as you added the first commit, push the branch (so other team member are aware of your work and can already see if any problems might arise -> if everyone just codes by themselves the collaboration is usually a lot worse)
4. Create a pull request from your branch to main
5. Add the issue number to the pull request title (e.g. `5: Create login/register endpoint`)
6. In the description of the pull request, add `-closes #5` to automatically close the issue when the pull request is merged
7. Assign the pull request to yourself
8. When you are done with the implementation take a look at the changes of the pull request in Github under "File changed" to see that everything is correct
9. If everything is correct, merge the pull request with the option "Squash and merge" (so we have a nice history with one commit per issue -> otherwise the commit history is bloated with commits)
10. (Optional) If you cannot merge your branch into main due to a conflict do the following steps:

- `git checkout main`
- `git pull`
- `git checkout YOUR-BRANCH` (e.g. `5-create-login-register-endpoint`)
- `git rebase -i main`
- Solve the conflicts with the help of your IDE
- Do a force push of your branch `git push -f`
- Now the conflicts should be solved and you can merge your branch into main through Github (with the option "Squash and merge")

## How to update production:

`git push --force origin main:production`
