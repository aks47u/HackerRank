N = int(raw_input())
name = []

for i in range(N):
    name.append([str(raw_input()), float(raw_input())])

name.sort()
number = []

for i in range(N):
    number.append(name[i][1])

mini = min(number)
small = max(number)

for i in range(N):
    if name[i][1] != mini and name[i][1] < small:
        small = name[i][1]

for i in range(N):
    if name[i][1] == small:
        print name[i][0]
