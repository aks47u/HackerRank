from collections import deque

for _ in range(int(input())):
    input()
    d = deque(int(x) for x in input().split())

    if d[0] < d[-1]:
        tmp = d.pop()
    else:
        tmp = d.popleft()

    while len(d) > 0:
        if tmp >= d[-1] and d[0] <= d[-1]:
            tmp = d.pop()
        elif tmp >= d[0]:
            tmp = d.popleft()
        else:
            print('No')
            d.clear()
            break

        if not len(d):
            print('Yes')
