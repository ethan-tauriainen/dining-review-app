#!/bin/sh

# shellcheck disable=SC2039
# shellcheck disable=SC2162

# take name in as command line argument
displayName=$1

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
-X PUT --data "$(generate_post_data)" "http://localhost:8080/api/user/$displayName"

echo