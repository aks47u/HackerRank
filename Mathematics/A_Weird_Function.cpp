#include <bits/stdc++.h>

using namespace std;

#define scanll(x) scanf("%lld",&x)
#define scan(x) scanf("%d",&x)
#define printll(x) printf("%lld\n",x)
#define print(x) printf("%d\n",x)
#define ll long long

int find() {
	ll sum = 0, num;
	num = 1000000000000LL;
	int i = 0;

	while (sum <= num) {
		sum += i;
		i++;
	}

	return i;
}

vector<int> etf;
vector<bool> P;
vector<ll> w_func;
int n;

void Weird_function() {
	w_func.resize(n, 0);

	for (int i = 1; i < n; i++) {
		if (i % 2) {
			w_func[i] = etf[i];
			w_func[i] = w_func[i] * etf[(i + 1) / 2];
		} else {
			w_func[i] = etf[i / 2];
			w_func[i] = w_func[i] * etf[i + 1];
		}
	}

	for (int i = 1; i < n; i++) {
		w_func[i] += w_func[i - 1];
	}
}

void ETF() {
	n = find();
	etf.resize(n + 1);
	P.resize(n + 1, 0);

	for (int i = 1; i <= n; i++) {
		etf[i] = i;
	}

	for (int i = 2; i <= n; i++) {
		if (!P[i]) {
			etf[i] -= (etf[i] / i);
			int j = 2 * i;

			while (j <= n) {
				etf[j] -= (etf[j] / i);
				P[j] = 1;
				j += i;
			}
		}
	}

	Weird_function();
}

ll test_cases(ll L, ll R) {
	ll l, r;
	l = (sqrt(1 + 8 * L) - 1) / 2;
	r = (sqrt(1 + 8 * R) - 1) / 2;

	if ((l * (l + 1)) == 2 * L) {
		return (w_func[r] - w_func[l - 1]);
	} else {
		return (w_func[r] - w_func[l]);
	}
}

int main() {
	int t;
	ETF();
	scan(t);
	ll MAXX = 1000000000000LL;
	assert(t <= 100000);

	while (t--) {
		ll L, R;
		scanll(L), scanll(R);
		assert(1 <= L && L <= MAXX && L <= R && R <= MAXX && R >= 1);
		printll(test_cases(L, R));
	}

	return 0;
}
