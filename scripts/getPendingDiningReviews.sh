#!/bin/bash

curl -i \
-H "Accept: application/json" \
-H "Content-Type: application/json" \
-X GET "http://localhost:8080/api/review/admin/pending"

echo
