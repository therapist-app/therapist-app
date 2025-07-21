#!/bin/bash

# --- Configuration ---
APP_DIR="/home/jonas/coach-app"
BRANCH="production"
LOG_FILE="/home/jonas/coach-app/deploy.log"

# --- Script ---

# Function to log messages with a timestamp.
log() {
  echo "$(date '+%Y-%m-%d %H:%M:%S') - $1" >> "$LOG_FILE"
}

# Ensure the script is not run as root.
if [ "$EUID" -eq 0 ]; then
  log "ERROR: This script should not be run as root. Exiting."
  exit 1
fi

# Navigate to the application directory. Exit immediately if this fails.
cd "$APP_DIR" || { log "ERROR: Could not navigate to directory $APP_DIR. Exiting."; exit 1; }

log "--- Starting deployment check for branch '$BRANCH' ---"

# Step 1: Fetch the latest changes from the remote repository.
# The '-q' flag makes the output quiet.
log "Fetching latest changes from remote..."
git fetch -q

# Step 2: Check for differences between the local and remote branch.
# We compare the commit hash of the local HEAD with the remote branch's HEAD.
LOCAL=$(git rev-parse @)
REMOTE=$(git rev-parse @{u})

if [ "$LOCAL" = "$REMOTE" ]; then
  # No changes detected.
  log "No new changes detected. Application is up to date."
  log "--- Deployment check finished ---"
  exit 0
else
  # Changes were detected.
  log "New changes detected. Starting deployment process."

  # Step 3: Pull the new changes from the production branch.
  log "Pulling new changes..."
  git pull origin "$BRANCH" || { log "ERROR: 'git pull' failed. Exiting."; exit 1; }

  # Step 4: Rebuild and restart the Docker Compose services.
  # 'docker compose' is the newer command syntax. Use 'docker-compose' if you have an older version.
  # '--force-recreate' ensures containers are recreated even if their configuration hasn't changed.
  # '--build' forces the build of images before starting containers.
  # '-d' runs the containers in detached mode.
  log "Rebuilding and restarting Docker containers..."
  docker compose up --force-recreate --build -d || { log "ERROR: 'docker compose up' failed. Exiting."; exit 1; }

  log "Deployment successful!"
  log "--- Deployment check finished ---"
fi

exit 0
