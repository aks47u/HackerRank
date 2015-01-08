while read line;
do
    echo "$line" | cut -d$'\t' -f2-
done
