from math import factorial as f

def ncr(n, r):
    if n < r:
        return 0
    return f(n) / (f(r) * f(n - r))

def factor(n):
    rr = []
    for i in range(2, 51):
        if n % i == 0:
            rr.append(i)
            n /= i
    return rr

def chinese(n, r, p):
    ni = []
    ri = []
    for i in range(32, -1, -1):
        ni.append(n / pow(p, i))
        ri.append(r / pow(p, i))
        if n >= p ** i:
            n %= p ** i
        if r >= p ** i:
            r %= p ** i
    prod = 1
    for i in range(33):
        prod *= ncr(ni[i], ri[i])
    return prod % p

def euclid(a, b):
    r0, r1 = a, b
    s0, s1 = 1, 0
    t0, t1 = 0, 1
    i = 0
    while r0 != 0 and r1 != 0:
        q = r0 / r1
        r0 = r0 - q * r1
        s0 = s0 - q * s1
        t0 = t0 - q * t1
        if r0 == 0:
            return s1, t1
        q = r1 / r0
        r1 = r1 - q * r0
        s1 = s1 - q * s0
        t1 = t1 - q * t0
        i = i + 1
    return s0, t0

for _ in range(input()):
    n, r, m = map(int, raw_input().split())
    if m == 1:
        print 0
        continue
    facs = factor(m)
    mods = map(lambda x:chinese(n, r, x), facs)
    bflag = 0
    sum1 = 0
    for i, j in zip(facs, mods):
        t = euclid(i, m / i)
        sum1 += (m * t[1] / i) * j
    print sum1 % m
