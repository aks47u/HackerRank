import collections

n, m = map(int, input().split())
buckets = collections.defaultdict(list)

for i in range(1, n + 1):
    buckets[input()].append(str(i))

m_words = [input() for i in range(m)]

for word in m_words:
    positions = buckets.get(word)
    print(" ".join(positions) if positions else -1)
