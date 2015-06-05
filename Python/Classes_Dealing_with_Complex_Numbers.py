import math

class Calc(object):
  def __init__(self, r, i):
    self.r = r
    self.i = i
 
[a,b] = [float(j) for j in raw_input().split()]
A = Calc(a, b)
[a,b] = [float(j) for j in raw_input().split()]
B = Calc(a, b)

addr = (A.r + B.r)
addi = (A.i + B.i)

if addi == 0:
  print "%.2f"%addr
elif addr == 0:
  print "%.2fi"%addi
else:
  if addi > 0:
    print "%.2f"%addr + " + " + "%.2fi"%addi
  else:
    print "%.2f"%addr + " - " + "%.2fi"%(addi*-1)

addr = (A.r - B.r)
addi = (A.i - B.i)

if addi == 0:
  print "%.2f"%addr
elif addr == 0:
  print "%.2fi"%addi
else:
  if addi > 0:
    print "%.2f"%addr + " + " + "%.2fi"%addi
  else:
    print "%.2f"%addr + " - " + "%.2fi"%(addi*-1)    

addr = (A.r * B.r - A.i * B.i)
addi = (A.r * B.i + B.r * A.i)

if addi == 0:
  print "%.2f"%addr
elif addr == 0:
  print "%.2fi"%addi
else:
  if addi > 0:
    print "%.2f"%addr + " + " + "%.2fi"%addi
  else:
    print "%.2f"%addr + " - " + "%.2fi"%(addi*-1)

addr = (A.r * B.r + A.i * B.i) / (B.r ** 2 + B.i ** 2)
addi = (B.r * A.i - A.r * B.i) / (B.r ** 2 + B.i ** 2)

if addi == 0:
  print "%.2f"%addr
elif addr == 0:
  print "%.2fi"%addi
else:
  if addi > 0:
    print "%.2f"%addr + " + " + "%.2fi"%addi
  else:
    print "%.2f"%addr + " - " + "%.2fi"%(addi*-1)        

print "%.2f"%math.sqrt(A.r ** 2 + A.i ** 2)
print "%.2f"%math.sqrt(B.r ** 2 + B.i ** 2)
