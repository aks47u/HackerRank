mod = 10 ** 9 + 7
N = 10 ** 5 + 1
fac = [1] * N
inv = [1] * N
ifc = [1] * N

for i in xrange(2, N):
    inv[i] = (mod - mod / i) * inv[mod % i] % mod
    fac[i] = i * fac[i - 1] % mod
    ifc[i] = inv[i] * ifc[i - 1] % mod

def Ch(n, r):
    return fac[n] * ifc[r] * ifc[n - r] % mod

def F(U, V, K, P):
    return inv[P] * (Ch(P * U, P * V) + (P * (K == 0) - 1) * Ch(U, V)) % mod

for cas in xrange(input()):
    U, V, K, P = map(int, raw_input().strip().split())
    print F(U, V, K, P)
