#!/bin/sh

# shellcheck disable=SC2039
# shellcheck disable=SC2162
read -p 'Name: ' name
read -p 'Zipcode: ' zipcode

generate_post_data()
{
	cat <<EOF
	{
		"name": "$name",
		"zipcode": "$zipcode"
	}
EOF
}

curl -i \
-H "Accept: application/json" \
-H "Content-Type: application/json" \
-X POST --data "$(generate_post_data)" "http://localhost:8080/api/restaurant"

echo