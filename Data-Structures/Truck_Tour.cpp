#include <algorithm>
#include <cmath>
#include <cstdio>
#include <iostream>
#include <vector>

using namespace std;

int n, p[100005], d[100005];

int main() {
	scanf("%d", &n);

	for (int i = 0; i < n; ++i) {
		scanf("%d%d", &p[i], &d[i]);
	}

	int ret = 0, amount = 0, sum = 0;

	for (int i = 0; i < n; ++i) {
		p[i] -= d[i];
		sum += p[i];

		if (amount + p[i] < 0) {
			amount = 0;
			ret = i + 1;
		} else {
			amount += p[i];
		}
	}

	printf("%d\n", sum >= 0 ? ret : -1);

	return 0;
}
