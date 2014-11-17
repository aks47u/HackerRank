IFS=$'\n' read -d '' -r -a lines
printf '%s\n' "${lines[@]}" | tr -d [a-z]
