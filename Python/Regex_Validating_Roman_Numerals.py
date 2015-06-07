import re

n = raw_input()
s = re.search(r"^M{0,3}(CM|CD|D?C{0,3})(XC|XL|L?X{0,3})(IX|IV|V?I{0,3})$",n)

if s:
  print True
else:
  print False
