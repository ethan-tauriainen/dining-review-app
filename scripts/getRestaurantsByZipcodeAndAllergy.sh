#!/bin/bash

# shellcheck disable=SC2162
read -p 'Zipcode: ' zipcode
read -p 'Allergy: ' allergy

curl -i \
-H "Accept: application/json" \
-H "Content-Type: application/json" \
-X GET "http://localhost:8080/api/restaurant?zipcode=$zipcode&allergy=$allergy"

echo
