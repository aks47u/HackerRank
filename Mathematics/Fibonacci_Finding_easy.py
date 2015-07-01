def pow_mod(x, y, z):
    number = [[1, 1], [1, 0]]
    while y:
        if y & 1:
            number = matmult(number, x, z)
        y >>= 1
        x = matmult(x, x, z)
    return number
 
def matmult(a, b, m):
    zip_b = zip(*b)
    return [[sum(ele_a * ele_b for ele_a, ele_b in zip(row_a, col_b)) % m for col_b in zip_b] for row_a in a]
 
mod = 10 ** 9 + 7
t = input()
a = [[1, 1], [1, 0]]
for i in range(t):
    A, B, N = map(int, raw_input().split())
    t = pow_mod(a, N - 1, mod)
    print (t[1][0] * B + t[1][1] * A) % mod
