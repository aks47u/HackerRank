#include <bits/stdc++.h>

#define SZ(X) ((int)(X).size())
#define ALL(X) (X).begin(), (X).end()
#define REP(I, N) for (int I = 0; I < (N); ++I)
#define REPP(I, A, B) for (int I = (A); I < (B); ++I)
#define RI(X) scanf("%d", &(X))
#define RII(X, Y) scanf("%d%d", &(X), &(Y))
#define RIII(X, Y, Z) scanf("%d%d%d", &(X), &(Y), &(Z))
#define DRI(X) int (X); scanf("%d", &X)
#define DRII(X, Y) int X, Y; scanf("%d%d", &X, &Y)
#define DRIII(X, Y, Z) int X, Y, Z; scanf("%d%d%d", &X, &Y, &Z)
#define RS(X) scanf("%s", (X))
#define CASET int ___T, case_n = 1; scanf("%d ", &___T); while (___T-- > 0)
#define MP make_pair
#define PB push_back
#define MS0(X) memset((X), 0, sizeof((X)))
#define MS1(X) memset((X), -1, sizeof((X)))
#define LEN(X) strlen(X)
#define F first
#define S second
typedef long long LL;

using namespace std;

const int MAXLEN = (int) 1e5 + 5;
char s[MAXLEN];
int SA[MAXLEN], cnt[MAXLEN], ary1[MAXLEN], ary2[MAXLEN];
int *Rank, *Height;

inline bool cmp(int *r, int a, int b, int l) {
	return r[a] == r[b] && r[a + l] == r[b + l];
}

void make_suffix_array(int MSIZE, int len) {
	int p, *x, *y, *tmp, i, j, k;
	x = ary1;
	y = ary2;
	memset(cnt, 0, sizeof(int) * MSIZE);

	for (i = 0; i < len; i++) {
		cnt[x[i] = s[i]]++;
	}

	for (i = 1; i < MSIZE; i++) {
		cnt[i] += cnt[i - 1];
	}

	for (i = len - 1; i >= 0; i--) {
		SA[--cnt[x[i]]] = i;
	}

	for (j = p = 1; p < len; j <<= 1, MSIZE = p) {
		for (p = 0, i = len - j; i < len; i++) {
			y[p++] = i;
		}

		for (i = 0; i < len; i++) {
			if (SA[i] >= j) {
				y[p++] = SA[i] - j;
			}
		}

		memset(cnt, 0, sizeof(int) * MSIZE);

		for (i = 0; i < len; i++) {
			cnt[x[y[i]]]++;
		}

		for (i = 1; i < MSIZE; i++) {
			cnt[i] += cnt[i - 1];
		}

		for (i = len - 1; i >= 0; i--) {
			SA[--cnt[x[y[i]]]] = y[i];
		}

		tmp = x;
		x = y;
		y = tmp;
		x[SA[0]] = 0;

		for (i = p = 1; i < len; i++) {
			x[SA[i]] = cmp(y, SA[i - 1], SA[i], j) ? p - 1 : p++;
		}
	}

	Rank = x;
	Height = y;

	for (i = k = 0; i < len - 1; i++) {
		if (k > 0) {
			k--;
		}

		j = SA[Rank[i] - 1];

		while (s[i + k] == s[j + k]) {
			k++;
		}

		Height[Rank[i]] = k;
	}
}

LL get(LL x, LL y) {
	return (x + y) * (y - x + 1) / 2;
}

int main() {
	CASET {
		RS(s);
		int n = LEN(s);
		LL K;
		cin >> K;
		make_suffix_array(128, n + 1);
		int now = 0;

		REPP(i, 1, n + 1) {
			now = Height[i];

			if(K <= get(now + 1, n - SA[i])) {
				LL ll = now + 1, rr = n - SA[i];

				while(ll < rr) {
					LL mm = (ll + rr) >> 1;

					if(get(now + 1, mm) < K) {
						ll = mm + 1;
					} else {
						rr = mm;
					}
				}

				K -= get(now + 1, ll - 1);
				printf("%c\n", s[SA[i] + K - 1]);
				break;
			} else {
				K -= get(now + 1, n - SA[i]);
			}
		}
	}

	return 0;
}
