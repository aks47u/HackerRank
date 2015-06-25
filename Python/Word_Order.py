import collections

class OrderedCounter(
    collections.Counter, collections.OrderedDict
):
    pass

n = int(input())
buckets = OrderedCounter()

for i in range(n):
    buckets[input()] += 1

print(len(buckets))
print(
    " ".join(
        (str(count) for count in buckets.values())
    )
)
