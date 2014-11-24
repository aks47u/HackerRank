while read line;
do
    echo "$line" | cut -d' ' -f4
done
