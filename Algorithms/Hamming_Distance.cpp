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

const int LEN = 20;
const int BOUND = 1 << LEN;
int rev[BOUND], bit_cnt[BOUND];
int ONES;

void init() {
	REP(i,LEN)
		ONES |= 1 << i;

	REP(i,BOUND)
	{
		bit_cnt[i] = bit_cnt[i >> 1] + (i & 1);
		int tmp = i;

		REP(j,LEN)
		{
			rev[i] <<= 1;
			rev[i] |= tmp & 1;
			tmp >>= 1;
		}
	}
}

int a[50010];
char s[50010];
int tmp1[50010], tmp2[50010], tmp3[50010];

void get(int tmp[], int st, int len) {
	if (!len) {
		tmp[0] = 0;

		return;
	}

	int w = len / LEN;
	int r = st % LEN;
	int x = st / LEN;
	int mask1 = (1 << r) - 1;
	int mask2 = (1 << LEN) - 1 - mask1;

	REP(i,w)
	{
		tmp[i] = ((a[x + i] & mask2) >> r)
						| ((a[x + i + 1] & mask1) << (LEN - r));
	}

	r = len - LEN * w;
	tmp[w] = 0;

	REP(i,r)
	{
		if ((a[(st + w * LEN + i) / LEN] >> ((st + w * LEN + i) % LEN)) & 1) {
			tmp[w] |= 1 << i;
		}
	}
}

void put(int tmp[], int st, int len) {
	if (!len) {
		return;
	}

	int w = len / LEN;
	int r = st % LEN;
	int x = st / LEN;
	int mask1 = (1 << r) - 1;
	int mask2 = (1 << LEN) - 1 - mask1;
	int mask3 = (1 << (LEN - r)) - 1;
	int mask4 = (1 << LEN) - 1 - mask3;

	REP(i,w)
	{
		a[x + i] &= mask1;
		a[x + i] |= (tmp[i] & mask3) << r;
		a[x + i + 1] &= mask2;
		a[x + i + 1] |= (tmp[i] & mask4) >> (LEN - r);
	}

	r = len - LEN * w;
	int xx = (st + w * LEN) / LEN;
	int yy = (st + w * LEN) % LEN;

	REP(i,r)
	{
		if (((tmp[w] >> i) & 1) != ((a[xx] >> yy) & 1)) {
			a[xx] ^= 1 << yy;
		}

		yy++;

		if (yy == LEN) {
			xx++;
			yy = 0;
		}
	}
}

int main() {
	init();
	DRII(N, M);
	RS(s);

	int sn = LEN(s);
	{
		int x = 0, y = 0;

		REP(i,sn)
		{
			if (s[i] == 'b') {
				a[x] |= 1 << y;
			}

			y++;

			if (y == LEN) {
				x++;
				y = 0;
			}
		}
	}

	DRI(Q);

	while (Q--) {
		char c[4];
		RS(c);

		if (c[0] == 'C') {
			DRII(ll, rr);
			ll--;
			rr--;
			int st = (ll + LEN - 1) / LEN, ed = (rr + 1) / LEN;
			RS(c);
			int len = (rr - ll + LEN) / LEN;

			if (c[0] == 'a') {
				REP(i,len)
					tmp1[i] = 0;
			} else {
				REP(i,len)
					tmp1[i] = ONES;
			}

			put(tmp1, ll, rr - ll + 1);
		} else if (c[0] == 'S') {
			DRII(ll1, rr1);
			DRII(ll2, rr2);
			ll1--;
			rr1--;
			ll2--;
			rr2--;
			get(tmp1, ll1, rr1 - ll1 + 1);
			get(tmp2, rr1 + 1, ll2 - 1 - rr1);
			get(tmp3, ll2, rr2 - ll2 + 1);
			put(tmp3, ll1, rr2 - ll2 + 1);
			put(tmp2, ll1 + rr2 - ll2 + 1, ll2 - 1 - rr1);
			put(tmp1, ll1 + rr2 - rr1, rr1 - ll1 + 1);
		} else if (c[0] == 'R') {
			DRII(ll, rr);
			ll--;
			rr--;
			int len = rr - ll + 1;
			get(tmp1, ll, len);

			if (len % LEN == 0) {
				int w = len / LEN;
				REP(i,w)
				tmp2[i] = rev[tmp1[w - i - 1]];
			} else {
				int w = len / LEN;
				int r = len % LEN;
				int mask1 = (1 << r) - 1;
				int mask2 = (1 << LEN) - 1 - mask1;
				REP(i,w)
					tmp2[i] = rev[((tmp1[w - i] & mask1) << (LEN - r))
							  | ((tmp1[w - i - 1] & mask2) >> r)];
				tmp2[w] = 0;

				REP(i,r)
					if ((tmp1[0] >> (r - i - 1)) & 1) {
						tmp2[w] |= 1 << i;
					}
			}

			put(tmp2, ll, len);
		} else if (c[0] == 'W') {
			DRII(ll, rr);
			ll--;
			int x = ll / LEN;
			int y = ll % LEN;

			REPP(i,ll,rr)
			{
				int now = (a[x] >> y) & 1;

				if (now) {
					putchar('b');
				} else {
					putchar('a');
				}

				y++;

				if (y == LEN) {
					x++;
					y = 0;
				}
			}

			puts("");
		} else if (c[0] == 'H') {
			DRIII(st1, st2, len);
			st1--;
			st2--;
			get(tmp1, st1, len);
			get(tmp2, st2, len);
			len = (len + LEN - 1) / LEN;
			int an = 0;
			REP(i,len)
			an += bit_cnt[tmp1[i] ^ tmp2[i]];
			printf("%d\n", an);
		}
	}

	return 0;
}
