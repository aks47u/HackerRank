#include <cmath>
#include <set>
#include <cstdio>
#include <vector>
#include <iostream>
#include <algorithm>
using namespace std;

#define MAX 100000

int n, k;
long long a[MAX];
multiset<long long> s;

void rec(int m, int idx, int at, long long sum) {
	if (at == k - 1) {
		s.erase(s.find(sum));
	} else {
		rec(m, idx, at + 1, sum + a[idx]);

		if (idx + 1 < m) {
			rec(m, idx + 1, at, sum);
		}
	}
}

int main() {
	int T;
	scanf("%d", &T);

	while (T--) {
		s.clear();
		scanf("%d%d", &n, &k);
		char c;
		scanf("%c", &c);

		while (true) {
			long long temp;
			scanf("%lld%c", &temp, &c);
			s.insert(temp);

			if (c == '\n') {
				break;
			}
		}

		a[0] = *s.begin() / k;
		s.erase(s.begin());

		for (int i = 1; i < n; i++) {
			a[i] = ((*s.begin()) - a[0] * (k - 1));
			rec(i + 1, 0, 0, a[i]);
		}

		for (int i = 0; i < n - 1; i++) {
			printf("%lld ", a[i]);
		}

		printf("%lld\n", a[n - 1]);
	}

	return 0;
}
