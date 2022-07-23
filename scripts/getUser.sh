#!/bin/bash

# remember to provide name as command line argument
displayName=$1

curl -i \
-H "Accept: application/json" \
-H "Content-Type: application/json" \
-X GET "http://localhost:8080/api/user/$displayName"

echo
