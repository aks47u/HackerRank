#include <algorithm>
#include <cmath>
#include <complex>
#include <cstdio>
#include <cstdlib>
#include <cstring>
#include <iomanip>
#include <iostream>
#include <map>
#include <set>
#include <string>
#include <vector>

using namespace std;

int n, K;

struct element {
	int val;
	int index;

	bool operator <(element that) const {
		return val < that.val;
	}

} E[100001];

int toLeft[100001], toRight[100001];
int indexToRank[100001];
int s[1000001];

int lowbit(int x) {
	return x & (-x);
}

inline void update(int pos, int v) {
	while (pos <= n) {
		s[pos] += v;
		pos += lowbit(pos);
	}
}

inline int query(int pos) {
	int ret = 0;

	while (pos) {
		ret += s[pos];
		pos -= lowbit(pos);
	}

	return ret;
}

int SQRTN = 330;
int nQuery;
long long ans[100001];

struct query {
	int L, R;
	int index;

	bool operator <(query that) const {
		if (L / SQRTN != that.L / SQRTN) {
			return L / SQRTN < that.L / SQRTN;
		}

		if ((L / SQRTN) % 2 == 0) {
			return R / SQRTN < that.R / SQRTN;
		}

		return R / SQRTN > that.R / SQRTN;
	}
} Q[100001];

int curL = 1, curR = 0;
long long curAns = 0;

inline long long evaluation() {
	long long ret = 0;

	for (int i = 2; i <= nQuery; i++) {
		ret += abs(Q[i].L - Q[i - 1].L) + abs(Q[i].R - Q[i - 1].R);
	}

	return ret;
}

inline void addIt(int x) {
	x = indexToRank[x];
	curAns += query(toRight[x]) - query(toLeft[x] - 1);
	update(x, 1);
}

inline void delIt(int x) {
	x = indexToRank[x];
	update(x, -1);
	curAns -= query(toRight[x]) - query(toLeft[x] - 1);
}

int MAIN() {
	scanf("%d %d", &n, &K);

	for (int i = 1; i <= n; i++) {
		scanf("%d", &E[i].val);
		E[i].index = i;
	}

	sort(E + 1, E + 1 + n);

	for (int i = 1; i <= n; i++) {
		indexToRank[E[i].index] = i;
	}

	int p = 1;

	for (int i = 1; i <= n; i++) {
		while (E[i].val - E[p].val > K) {
			p++;
		}

		toLeft[i] = p;
	}

	p = n;

	for (int i = n; i >= 1; i--) {
		while (E[p].val - E[i].val > K) {
			p--;
		}

		toRight[i] = p;
	}

	scanf("%d", &nQuery);

	for (int i = 1; i <= nQuery; i++) {
		scanf("%d %d", &Q[i].L, &Q[i].R), Q[i].index = i;
	}

	int old_sqrtN = SQRTN;
	long long bestSqurn = 0, bestV = 1000000000000LL;

	for (int i = old_sqrtN - 20; i <= old_sqrtN + 20; i += 10) {
		SQRTN = i;
		sort(Q + 1, Q + 1 + nQuery);
		long long v = evaluation();

		if (v < bestV) {
			bestV = v;
			bestSqurn = i;
		}
	}

	SQRTN = bestSqurn;
	sort(Q + 1, Q + 1 + nQuery);
	memset(s, 0, sizeof(s));

	for (int i = 1; i <= nQuery; i++) {
		int L = Q[i].L + 1, R = Q[i].R + 1;

		if (curL > R || curR < L) {
			while (curL <= curR) {
				delIt(curL);
				curL++;
			}

			curL = L;
			curR = L - 1;
		}

		while (curR < R) {
			addIt(curR + 1);
			curR += 1;
		}

		while (curL > L) {
			addIt(curL - 1);
			curL--;
		}

		while (curR > R) {
			delIt(curR);
			curR--;
		}

		while (curL < L) {
			delIt(curL);
			curL++;
		}

		ans[Q[i].index] = curAns;
	}

	for (int i = 1; i <= nQuery; i++) {
		printf("%lld\n", ans[i]);
	}

	return 0;
}

int main() {
	return MAIN();
}
