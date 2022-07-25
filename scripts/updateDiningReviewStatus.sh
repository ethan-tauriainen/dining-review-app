#!/bin/sh

# shellcheck disable=SC2039
# shellcheck disable=SC2162

read -p 'Id: ' id
read -p 'Status: ' status

generate_post_data()
{
	cat <<EOF
	{
		"status": "$status"
	}
EOF
}

curl -i \
-H "Accept: application/json" \
-H "Content-Type: application/json" \
-X PUT --data "$(generate_post_data)" "http://localhost:8080/api/review/admin/id/$id/status/$status"

echo