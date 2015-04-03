from math import sqrt

def correlation(x, y):
    n = len(x)
    d1 = sqrt(n * sum(map(lambda i: i ** 2, x)) - (sum (x)) ** 2)
    d2 = sqrt(n * sum(map(lambda i: i ** 2, y)) - (sum (y)) ** 2)
    n1 = 0
    for i in range(n):
        n1 += n * x[i] * y[i]
    n2 = (sum (x)) * (sum (y))
    return (n1 - n2) / (d1 * d2)

if __name__ == "__main__":
    N = int(raw_input())
    x, y, z = [], [], []
    for i in range(N):
        a, b, c = map(float, raw_input().split())
        x.append(a)
        y.append(b)
        z.append(c)
    print '%.2f' % correlation(x, y)
    print '%.2f' % correlation(y, z)
    print '%.2f' % correlation(x, z)
