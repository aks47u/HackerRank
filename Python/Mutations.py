s = input()
i, c = input().split()
i = int(i)
print(s[:i] + c + s[(i + 1):])
