#include<bits/stdc++.h>
using namespace std;

#define MOD 1000000007
typedef long long LL;
#define assn(n,a,b) assert(n>=a && n<=b)
LL a[100006], b[100006], c[100006], counti[100006] = { };

int main() {
	memset(counti, -1, sizeof(counti));
	LL n, m, i, j;
	cin >> n >> m;
	assn(n, 1, 100000);
	assn(m, 1, 100000);

	for (i = 1; i <= n; i++) {
		cin >> a[i];
		assn(a[i], 1, 100000);
	}

	for (i = 1; i <= m; i++) {
		cin >> b[i];
		assn(b[i], 1, n);
	}

	for (i = 1; i <= m; i++) {
		cin >> c[i];
		assn(c[i], 1, 100000);
	}

	for (i = 1; i <= m; i++) {
		if (counti[b[i]] == -1) {
			counti[b[i]] = c[i];
		} else {
			counti[b[i]] = (counti[b[i]] * c[i]) % MOD;
		}
	}

	for (i = 1; i <= n; i++) {
		for (j = 1; (j * i) <= n; j++) {
			if (counti[i] != -1) {
				a[j * i] = (a[j * i] * counti[i]) % MOD;
			}
		}
	}

	for (i = 1; i < n; i++) {
		cout << a[i] << " ";
	}

	cout << a[n] << endl;

	return 0;
}
