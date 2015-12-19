import math

n = input()

def ang(x, y):
    if math.atan2(y, x) < 0:
        return (x, y, 2.0 * math.pi + math.atan2(y, x))
    else:
        return (x, y, math.atan2(y, x))

M = []

while n:
    n -= 1
    a, b = map(int, raw_input().split())
    M.append(ang(a, b))

for i in sorted(M, key=lambda x:(x[2], x[0] ** 2 + x[1] ** 2)):
    print i[0], i[1]
