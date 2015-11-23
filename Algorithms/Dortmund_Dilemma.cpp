#include <bits/stdc++.h>

using namespace std;

#define REP(i,a,b) for(i=a;i<b;i++)
#define rep(i,n) REP(i,0,n)
#define mygc(c) (c)=getchar_unlocked()
#define mypc(c) putchar_unlocked(c)
#define ll long long
#define ull unsigned ll
#define MD 1000000009

void reader(int *x) {
	int k, m = 0;
	*x = 0;

	for (;;) {
		mygc(k);

		if (k == '-') {
			m = 1;
			break;
		}

		if ('0' <= k && k <= '9') {
			*x = k - '0';
			break;
		}
	}

	for (;;) {
		mygc(k);

		if (k < '0' || k > '9') {
			break;
		}

		*x = (*x) * 10 + k - '0';
	}

	if (m) {
		(*x) = -(*x);
	}
}

void reader(int *x, int *y) {
	reader(x);
	reader(y);
}

void writer(int x, char c) {
	int i, sz = 0, m = 0;
	char buf[10];

	if (x < 0) {
		m = 1, x = -x;
	}

	while (x) {
		buf[sz++] = x % 10, x /= 10;
	}

	if (!sz) {
		buf[sz++] = 0;
	}

	if (m) {
		mypc('-');
	}

	while (sz--) {
		mypc(buf[sz] + '0');mypc(c);
	}
}

ull pw(ull a, ull b, ull m) {
	ull r = 1;

	while (b) {
		if (b & 1) {
			r = r * a % m;
		}

		b >>= 1;
		a = a * a % m;
	}

	return r;
}

int T, N, K;
ll dp[27][100100];
ll C[27][27];
ll res;

int solve(int N, int K) {
	int i, j, k;
	int arr[100];
	int pw[100];
	int res = 0;
	pw[0] = 1;
	REP(i,1,100)
	pw[i] = pw[i - 1] * K;

	rep(k,pw[N]) {
		j = k;
		rep(i,N)
		arr[i] = j % K, j /= K;

		REP(i,1,N) {
			rep(j,i)

			if (arr[j] != arr[N - i + j]) {
				break;
			}

			if (j == i) {
				res++;
				break;
			}
		}
	}

	return res;
}

int main() {
	int i, j;
	ll dp2[30];

	rep(i,27) {
		dp[i][0] = 1;

		REP(j,1,100100) {
			if (j % 2 == 1) {
				dp[i][j] = i * dp[i][j - 1] % MD;
			} else {
				dp[i][j] = (i * dp[i][j - 1] - dp[i][j / 2] + MD) % MD;
			}
		}
	}

	rep(i,27) {
		rep(j,100100)
		dp[i][j] = (pw(i, j, MD) - dp[i][j] + MD) % MD;
	}

	rep(j,27)
	C[0][j] = 0;
	rep(i,27)
	C[i][0] = 1;
	REP(i,1,27)
	REP(j,1,27)
	C[i][j] = (C[i - 1][j - 1] + C[i - 1][j]) % MD;
	reader(&T);

	while (T--) {
		reader(&N, &K);

		REP(i,1,27) {
			dp2[i] = dp[i][N];
			REP(j,1,i)
			dp2[i] -= C[i][j] * dp2[j] % MD;
			dp2[i] = ((dp2[i] % MD) + MD) % MD;
		}

		res = (dp2[K] * C[26][K]) % MD;
		writer(res, '\n');
	}

	return 0;
}
