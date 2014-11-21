while read line;
do
    echo  "$line" | cut -d$'\t' -f1-3
done
