#include <algorithm>
#include <cmath>
#include <cstdio>
#include <cstring>
#include <iostream>
#include <map>
#include <queue>
#include <set>
#include <stack>
#include <string>
#include <vector>

using namespace std;

#define SZ(x) ( (int) (x).size() )
#define dbg(x) cerr << #x << " = " << x << endl;
#define mp make_pair
#define pb push_back
#define fi first
#define se second

typedef long long ll;
typedef pair<int, int> pii;

const int MAX_N = 2e5 + 1;
const int POINT = -1;
const int QUERY = +1;
int N;
ll W, H;
int px[MAX_N], py[MAX_N];
set<int> E[MAX_N];

struct fenwick {
	vector<ll> t;
	vector<ll> a;

	void clear() {
		t.clear();
		a.clear();
	}

	void add(ll x) {
		a.pb(x);
	}

	void init() {
		sort(a.begin(), a.end());
		t.assign(SZ(a) + 1, 0);
	}

	void update(ll val, ll x) {
		int i = lower_bound(a.begin(), a.end(), val) - a.begin();

		for (++i; i < SZ(t); i += i & -i) {
			t[i] += x;
		}
	}

	ll query(ll val) {
		int i = lower_bound(a.begin(), a.end(), val) - a.begin();
		ll s = 0;

		for (++i; i > 0; i -= i & -i) {
			s += t[i];
		}

		return s;
	}
}

fwt[MAX_N];

struct point {
	ll x, y;
	int c, e;

	bool operator<(const point& o) const {
		if (x != o.x) {
			return x < o.x;
		}

		if (y != o.y) {
			return y < o.y;
		}

		return e < o.e;
	}

}

pts[MAX_N * 2];
int npts;
int ts[MAX_N];

void dfs(int u, int p) {
	ts[u] = 1;

	for (int v : E[u]) {
		if (v != p) {
			dfs(v, u);
			ts[u] += ts[v];
		}
	}
}

int center(int u) {
	int p = -1, s = ts[u];

	while (true) {
		int n = -1;

		for (int v : E[u]) {
			if (v != p && ts[v] * 2 > s) {
				n = v;
				break;
			}
		}

		if (n == -1) {
			return u;
		}

		p = u;
		u = n;
	}

	return u;
}

void getPoints(int u, int p, ll a, ll b, int c) {
	for (int v : E[u]) {
		if (p != v) {
			ll na = a + abs(px[u] - px[v]);
			ll nb = b + abs(py[u] - py[v]);
			int nc = (c == -1 ? v : c);
			pts[npts++] = {na, nb, nc, POINT};
			pts[npts++] = {W - na, H - nb, nc, QUERY};
			getPoints(v, u, na, nb, nc);
		}
	}
}

ll geometry(int u) {
	ll ret = 0;
	fwt[u].clear();

	for (int v : E[u]) {
		fwt[v].clear();
	}

	npts = 0;
	getPoints(u, -1, 0, 0, -1);

	for (int i = 0; i < npts; i++) {
		fwt[pts[i].c].add(pts[i].y);
		fwt[u].add(pts[i].y);
	}

	fwt[u].init();

	for (int v : E[u]) {
		fwt[v].init();
	}

	sort(pts, pts + npts);

	for (int i = 0; i < npts; i++) {
		if (pts[i].e == POINT) {
			fwt[u].update(pts[i].y, 1);
			fwt[pts[i].c].update(pts[i].y, -1);
		} else {
			ret += fwt[u].query(pts[i].y) + fwt[pts[i].c].query(pts[i].y);
		}
	}

	ret /= 2;

	for (int i = 0; i < npts; i++) {
		ret += (pts[i].x <= W && pts[i].y <= H && pts[i].e == POINT);
	}

	return ret;
}

ll dq(int u) {
	dfs(u, -1);
	u = center(u);
	ll ans = geometry(u);

	for (int v : E[u]) {
		E[v].erase(u);
		ans += dq(v);
	}

	return ans;
}

ll solve() {
	return 1LL * N * (N - 1) / 2 - dq(1);
}

int main() {
	int u, v;
	scanf("%d%lld%lld", &N, &W, &H);

	for (int i = 1; i <= N; i++) {
		scanf("%d%d", px + i, py + i);
	}

	for (int i = 0; i < N - 1; i++) {
		scanf("%d%d", &u, &v);
		E[u].insert(v);
		E[v].insert(u);
	}

	printf("%lld\n", solve());

	return 0;
}
