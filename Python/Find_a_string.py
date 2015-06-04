import fileinput

string, substring = fileinput.input()
count = len([True for i in range(0, len(string) - len(substring)) if string[i:i + len(substring)] == substring])
print(count)
