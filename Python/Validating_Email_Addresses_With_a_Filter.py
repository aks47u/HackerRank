import re

n = int(raw_input())
blank = []

for i in range(n):
  s = raw_input()
  flag = 0
  match = re.search(r'^[\w\d-]+\@[a-zA-Z0-9]+\.\w?\w?\w?$',s)
  
  if match:
    blank.append(s)
  else:
    pass

blank.sort()
print blank
