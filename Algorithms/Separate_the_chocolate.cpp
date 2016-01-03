#include <cassert>
#include <cstdio>
#include <cstring>
#include <map>
#include <string>

using namespace std;

typedef unsigned long long llu;

struct node {
	int num;
	char a[9];
	char no;
	char vwb;
	char dwb;
};

int m, n, last, now, pp, un;
llu ans;
char s[10][10];

inline bool operator<(const node &a, const node &b) {
	if (a.no < b.no) {
		return true;
	}

	if (a.no > b.no) {
		return false;
	}

	if (a.dwb < b.dwb) {
		return true;
	}

	if (a.dwb > b.dwb) {
		return false;
	}

	if (a.vwb < b.vwb) {
		return true;
	}

	if (a.vwb > b.vwb) {
		return false;
	}

	if (a.num < b.num) {
		return true;
	}

	if (a.num > b.num) {
		return false;
	}

	for (int i = 0; i < n; ++i) {
		if (a.a[i] < b.a[i]) {
			return true;
		}

		if (a.a[i] > b.a[i]) {
			return false;
		}
	}

	return false;
}

map<node, llu> save[2];

inline bool iswhite(int x) {
	return !(x & 1);
}

inline bool isblack(int x) {
	return (x & 1);
}

void makelone(node &temp, int y, int x, int n) {
	int i, j, z = (y << 1) + x;
	temp.a[y] = z;
	z = (y << 1);

	for (i = y + 1; i < n; ++i) {
		if (temp.a[i] == z) {
			break;
		}
	}

	for (j = i, i <<= 1; j < n; ++j) {
		if (temp.a[j] == z) {
			temp.a[j] = i;
		}
	}

	z = (y << 1) | 1;

	for (i = y + 1; i < n; ++i) {
		if (temp.a[i] == z) {
			break;
		}
	}

	for (j = i, i = (i << 1) | 1; j < n; ++j) {
		if (temp.a[j] == z) {
			temp.a[j] = i;
		}
	}
}

void makeunion(node &temp, int x, int y, int n) {
	if (x < y) {
		x ^= y ^= x ^= y;
	}

	for (int i = 0; i < n; ++i) {
		if (temp.a[i] == x) {
			temp.a[i] = y;
		}
	}
}

void makewhite(int x, int y, node temp, llu ans, int add) {
	bool yes;
	int i, j, k, ll, uu;
	map<node, llu>::iterator t;

	if ((temp.no == 0) || (temp.dwb == 0)) {
		return;
	}

	temp.num += add;

	if ((temp.num + un < -pp) || (temp.num - un > pp)) {
		return;
	}

	yes = (temp.dwb == 1);

	if ((x) &&(temp.a[y] == ((y << 1) | 1))) {
		for (i = y + 1; i < n; ++i) {
			if (temp.a[i] == temp.a[y]) {
				break;
			}
		}

		if (i >= n) {
			if ((temp.vwb != 1) && (temp.vwb != 2)) {
				return;
			}

			yes = true;
		}
	}

	ll = ((y) &&iswhite(temp.a[y - 1])) ? temp.a[y - 1] : (-1);
	uu = ((x) &&iswhite(temp.a[y])) ? temp.a[y] : (-1);
	k = x ? n : (y + 1);

	if (uu < 0) {
		makelone(temp, y, 0, k);

		if (ll >= 0) {
			temp.a[y] = ll;
		}
	} else if ((ll >= 0) && (ll != uu)) {
		makeunion(temp, ll, uu, k);
	}

	for (i = j = 0; i < k; ++i) {
		if ((temp.a[i] == (i << 1)) && (++j > 1)) {
			break;
		}
	}

	if (j == 1) {
		temp.vwb = ((temp.vwb == 1) || (temp.vwb == 2)) ? 2 : 0;
	} else {
		temp.vwb = ((temp.vwb == 1) || (temp.vwb == 2)) ? 1 : 3;
	}

	temp.dwb = yes ? 1 : 3;
	temp.no = ((uu >= 0) && (y + 1 < n) && ((temp.a[y + 1] & 1) == 0)) ? 0 : 2;
	save[now][temp] += ans;
}

void makeblack(int x, int y, node temp, llu ans, int add) {
	bool yes;
	int i, j, k, ll, uu;
	map<node, llu>::iterator t;

	if ((temp.no == 1) || (temp.dwb == 1)) {
		return;
	}

	temp.num += add;

	if ((temp.num + un < -pp) || (temp.num - un > pp)) {
		return;
	}

	yes = (temp.dwb == 0);

	if ((x) &&(temp.a[y] == (y << 1))) {
		for (i = y + 1; i < n; ++i) {
			if (temp.a[i] == temp.a[y]) {
				break;
			}
		}

		if (i >= n) {
			if ((temp.vwb != 0) && (temp.vwb != 2)) {
				return;
			}

			yes = true;
		}
	}

	ll = ((y) &&isblack(temp.a[y - 1])) ? temp.a[y - 1] : (-1);
	uu = ((x) &&isblack(temp.a[y])) ? temp.a[y] : (-1);
	k = x ? n : (y + 1);

	if (uu < 0) {
		makelone(temp, y, 1, k);

		if (ll >= 0) {
			temp.a[y] = ll;
		}
	} else if ((ll >= 0) && (ll != uu)) {
		makeunion(temp, ll, uu, k);
	}

	for (i = j = 0; i < k; ++i) {
		if ((temp.a[i] == ((i << 1) | 1)) && (++j > 1)) {
			break;
		}
	}

	if (j == 1) {
		temp.vwb = ((temp.vwb == 0) || (temp.vwb == 2)) ? 2 : 1;
	} else {
		temp.vwb = ((temp.vwb == 0) || (temp.vwb == 2)) ? 0 : 3;
	}

	temp.dwb = yes ? 0 : 3;
	temp.no = ((uu >= 0) && (y + 1 < n) && ((temp.a[y + 1] & 1) == 1)) ? 1 : 2;
	save[now][temp] += ans;
}

int main() {
	int z;
	node temp;
	scanf("%d%d%d", &m, &n, &pp);
	assert(0 <= m && m <= 8);
	assert(0 <= n && n <= 8);
	assert(0 <= pp <= m * n);
	memset(temp.a, 0, sizeof(temp.a));
	temp.num = 0;
	temp.no = temp.vwb = 2;
	temp.dwb = 3;
	save[0].clear();
	un = 0;

	for (int i = 0; i < m; ++i) {
		scanf("%s", s[i]);

		for (int j = 0; j < n; ++j) {
			if (s[i][j] == 'T') {
				++temp.num;
			} else if (s[i][j] == 'D') {
				--temp.num;
			} else {
				++un;
			}
		}
	}

	save[last = 0][temp] = 1;

	for (int i = 0; i < m; ++i) {
		for (int j = 0; j < n; ++j) {
			save[now = 1 ^ last].clear();

			if (s[i][j] == 'U') {
				--un;
			}

			for (map<node, llu>::iterator t = save[last].begin();
					t != save[last].end(); ++t) {
				if (s[i][j] == 'T') {
					makeblack(i, j, t->first, t->second, 0);
				} else if (s[i][j] == 'D') {
					makewhite(i, j, t->first, t->second, 0);
				} else {
					makeblack(i, j, t->first, t->second, 1);
					makewhite(i, j, t->first, t->second, -1);
				}
			}

			last = now;
		}
	}

	ans = 0;
	assert(un == 0);

	for (map<node, llu>::iterator t = save[last].begin(); t != save[last].end();
			++t) {
		if (t->first.vwb == 2) {
			assert((t->first.num >= -pp) && (t->first.num <= pp));
			ans += t->second;
		}
	}

	printf("%llu\n", ans);

	return 0;
}
