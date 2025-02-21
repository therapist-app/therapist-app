# Therapist App

## Backend

### How to run

1. Install openjdk-21, when typing `java --version` in the terminal the output should be `openjdk 21 2023-09-19`
2. Install docker and docker compose
3. `cd backend`
4. Copy the file `backend/.env.example` to `backend/.env`
5. `docker compose up -d` or (`docker-compose up -d`)
6. Visit `http://localhost:5050` in your browser to check if the database is running (email: admin@admin.com, password: admin)
7. On `http://localhost:5050` connect to DB -> right click "Servers" -> "Register Server" -> "name" = `therapy`, "Host name/address" = `therapy-db`, "Port" = `5433`, "Username" = `therapy-user`, "Password" = `therapy-password`, and "Save Password?" = `Yes`
8. `./gradlew bootRun`
9. Visit `http://localhost:8080/therapists` -> you should see a list with three therapists

## Frontend

### How to run

1. Install nodejs 22.14.0 -> when typing `node -v` in the terminal, the output should be `v22.14.0`
2. `cd frontend`
3. `npm install`
4. `npm run dev`
5. Visit `http://localhost:5173` in your browser, you should see a list with three therapists
6. Go to the register page (`http://localhost:5173/register`) and try creating a new therapist
