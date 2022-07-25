#!/bin/bash

# shellcheck disable=SC2162
read -p 'Id: ' id

curl -i \
-H "Accept: application/json" \
-H "Content-Type: application/json" \
-X GET "http://localhost:8080/api/restaurant/id/$id"

echo