#define _USE_MATH_DEFINES

#include <stdio.h>
#include <math.h>
#include <stdlib.h>
#include <ctype.h>
#include <string.h>
#include <iostream>
#include <sstream>
#include <vector>
#include <algorithm>
#include <set>
#include <queue>
#include <map>
#include <string>
#include <ctime>
#include <unordered_map>
#include <unordered_set>

#define M 1000000007

using namespace std;

int n, a[200], i, res[2];
unordered_map<int, int> fibs;

int gcd(int a, int b) {
	if (a && b) {
		while (a %= b ^= a ^= b ^= a)
			;
	}

	return a + b;
}

int getFib(int n) {
	auto it = fibs.find(n);

	if (it != fibs.end()) {
		return it->second;
	}

	int a[2][2];
	int b[2];
	int temp[2];
	int c[2][2];
	int i, j;

	for (i = 0; i < 2; i++) {
		for (j = 0; j < 2; j++) {
			a[i][j] = i || j;
		}

		b[i] = i;
	}

	while (n) {
		if (n % 2) {
			for (j = 0; j < 2; j++) {
				temp[j] = ((long long) b[0] * a[0][j]
						+ (long long) b[1] * a[1][j]) % M;
			}

			for (j = 0; j < 2; j++) {
				b[j] = temp[j];
			}
		}

		for (i = 0; i < 2; i++) {
			for (j = 0; j < 2; j++) {
				c[i][j] = ((long long) a[i][0] * a[0][j]
						+ (long long) a[i][1] * a[1][j]) % M;
			}
		}

		for (i = 0; i < 2; i++) {
			for (j = 0; j < 2; j++) {
				a[i][j] = c[i][j];
			}
		}

		n /= 2;
	}

	return b[0];
}

void rec(int i, int ans, int isZ) {
	if (i == n && ans) {
		res[isZ] = (long long) res[isZ] * getFib(ans) % M;

		return;
	}

	int g = gcd(ans, a[i]);

	if (g == ans) {
		return;
	}

	rec(i + 1, ans, isZ);
	rec(i + 1, g, !isZ);
}

int obr(int x) {
	int p = M, a = 0, b = 1, q;

	while (x) {
		q = p / x;
		p -= x * q;
		a -= b * q;
		p ^= x ^= p ^= x;
		a ^= b ^= a ^= b;
	}

	return (a + M) % M;
}

int main() {
	scanf("%d", &n);

	if (n < 1 || n > 100) {
		throw;
	}

	for (i = 0; i < n; i++) {
		scanf("%d", &a[i]);

		if (a[i] < 1 || a[i] > 1000000000) {
			throw;
		}
	}

	sort(a, a + n);
	n = unique(a, a + n) - a;
	res[0] = res[1] = 1;
	rec(0, 0, 1);
	res[0] = (long long) res[0] * obr(res[1]) % M;
	printf("%d\n", res[0]);

	return 0;
}
