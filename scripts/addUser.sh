#!/bin/sh

# shellcheck disable=SC2039
# shellcheck disable=SC2162
read -p 'Display name: ' displayName
read -p 'City: ' city
read -p 'State: ' state
read -p 'Zipcode: ' zipcode
read -p 'Is Peanut: ' isPeanut
read -p 'Is Egg: ' isEgg
read -p 'Is Dairy: ' isDairy

generate_post_data()
{
	cat <<EOF
	{
		"displayName": "$displayName",
		"city": "$city",
		"state": "$state",
		"zipcode": "$zipcode",
		"isPeanut": "$isPeanut",
		"isEgg": "$isEgg",
		"isDairy": "$isDairy"
	}
EOF
}

curl -i \
-H "Accept: application/json" \
-H "Content-Type: application/json" \
-X POST --data "$(generate_post_data)" "http://localhost:8080/api/user"

echo
