import re

count = 0

for i in range(int(raw_input())):
  s = raw_input()
  match = re.findall(r'=[\"\']',s)
  count += len(match)

print count  
