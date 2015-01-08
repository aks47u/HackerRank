IFS=$'\n' read -d '' -r -a lines
printf '%s\n' "${lines[@]}" | tr -s ' ' ' '
