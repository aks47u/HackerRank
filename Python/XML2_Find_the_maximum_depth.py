import re

stack = []
maxi = -1

for i in range(int(raw_input())):
  s = raw_input()
  match1 = re.findall(r'<\w',s)
  match2 = re.findall(r'</|/>',s)
  maxi = maxi + len(match1)
  stack.append(maxi)
  maxi = maxi - len(match2)
  stack.append(maxi)

print max(stack)
