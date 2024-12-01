#!/bin/bash

echo "Running suite and setting environment variables"

if [ -f .env ]; then
  export $(grep -v '^#' .env | xargs)
fi

./mvnw clean test -D TAGS=$1
