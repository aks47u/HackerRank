input()
i = [int(item) for item in input().split()]

a, b = (
    {int(item) for item in input().split()} for _ in range(2)
)

happiness = 0

for number in i:
    if number in a:
        happiness += 1
    elif number in b:
        happiness -= 1

print(happiness)
