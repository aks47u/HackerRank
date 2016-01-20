#include <iostream>
#include <cassert>
#include <cstdio>
#include <algorithm>
#include <map>

using namespace std;

int xsz[32], xbasis[32][32], n, i, a[1000000 + 5], death[1000000 + 5],
birth[1000000 + 5];
map<int, int> last_added;

void xorer_mk_clone(int x, int y) {
	xsz[x] = xsz[y];

	for (int i = 1; i <= xsz[x]; i++) {
		xbasis[x][i] = xbasis[y][i];
	}
}

void xorer_add(int x, int ai) {
	int i;

	for (i = 1; i <= xsz[x]; i++) {
		if ((ai ^ xbasis[x][i]) < ai) {
			ai ^= xbasis[x][i];
		}
	}

	if (!ai) {
		return;
	}

	xbasis[x][i = ++xsz[x]] = ai;

	while (i > 1 && xbasis[x][i] > xbasis[x][i - 1]) {
		swap(xbasis[x][i], xbasis[x][i--]);
	}
}

int xorer_optimal(int x) {
	int ret = 0;

	for (int i = 1; i <= xsz[x]; i++) {
		if ((ret ^ xbasis[x][i]) > ret) {
			ret ^= xbasis[x][i];
		}
	}

	return ret;
}

void solve(int lvl, int l, int r) {
	if (l == r) {
		if (a[l] > 0) {
			xorer_add(lvl, a[l]);
		}

		printf("%d\n", xorer_optimal(lvl));

		return;
	}

	int middle = (l + r) / 2;
	xorer_mk_clone(lvl + 1, lvl);

	for (i = middle + 1; i <= r; i++) {
		if (birth[i] < l && a[i] < 0) {
			xorer_add(lvl + 1, -a[i]);
		}
	}

	solve(lvl + 1, l, middle);
	xorer_mk_clone(lvl + 1, lvl);

	for (i = l; i <= middle; i++) {
		if (death[i] > r && a[i] > 0) {
			xorer_add(lvl + 1, a[i]);
		}
	}

	solve(lvl + 1, middle + 1, r);
}

int main(int argc, char * const argv[]) {
	scanf("%d", &n);

	for (i = 1; i <= n; i++) {
		scanf("%d", &a[i]);
		assert(a[i] != 0);
	}

	for (i = 1; i <= n; i++) {
		if (a[i] > 0) {
			last_added[a[i]] = i;
			death[i] = n + 1;
		} else {
			birth[i] = last_added[-a[i]];
			death[last_added[-a[i]]] = i;
		}
	}

	solve(0, 1, n);

	return 0;
}
