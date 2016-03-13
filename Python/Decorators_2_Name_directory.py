import re

M = []

for i in range(int(raw_input())):
  s = raw_input()
  match = re.findall(r'(\w+\s\w+)\s(\d+)\s(\w)', s)
  M.append(match[0])

M = sorted(M, key = lambda student: student[1])

for i in range(len(M)):
  if M[i][2] == 'M':
    print "Mr. " + M[i][0]
  
  if M[i][2] == 'F':
    print "Ms. " + M[i][0]
