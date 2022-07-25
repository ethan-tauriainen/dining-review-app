#!/bin/sh

# shellcheck disable=SC2039
# shellcheck disable=SC2162
read -p 'Submitted by: ' submittedBy
read -p 'Restaurant ID: ' restaurantId
read -p 'Peanut Score: ' peanutScore
read -p 'Egg Score: ' eggScore
read -p 'Dairy Score: ' dairyScore
read -p 'Commentary: ' commentary

generate_post_data()
{
	cat <<EOF
	{
		"submittedBy": "$submittedBy",
		"restaurantId": "$restaurantId",
		"peanutScore": "$peanutScore",
		"eggScore": "$eggScore",
		"dairyScore": "$dairyScore",
		"commentary": "$commentary"
	}
EOF
}

curl -i \
-H "Accept: application/json" \
-H "Content-Type: application/json" \
-X POST --data "$(generate_post_data)" "http://localhost:8080/api/review"

echo