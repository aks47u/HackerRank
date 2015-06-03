M = raw_input()
m = [int(i) for i in raw_input().split()]
N = raw_input()
n = [int(i) for i in raw_input().split()]
m.sort()
n.sort()
s1 = set(m)
s2 = set(n)
s3 = s1.symmetric_difference(s2)
s = list(s3)
s.sort()

for i in range(len(s)):
  print s[i]
