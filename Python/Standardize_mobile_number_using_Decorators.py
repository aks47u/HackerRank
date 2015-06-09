import re

stack = []

for i in range(int(raw_input())):
  s = raw_input()
  match = re.findall(r'\w?\w?(\d\d\d\d\d\d\d\d\d\d)',s)
  stack.append(match[0])

stack.sort()

for item in stack:
  print "+91 " + item[0:5] + " " + item[5:]
