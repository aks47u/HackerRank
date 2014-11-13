total=0
read N

for var in `seq 1 $N`
do
    read M
    total=$((total + M))
done

RESULT=$(awk "BEGIN {printf \"%.3f\",${total}/${N}}")
echo $RESULT
