#include <iostream>
#include <cstdio>
#include <map>
#include <cassert>

using namespace std;

const int maxB = 4;
const int maxM = 6;
const int maxL = 8;
const int maxS = 11881376;
const int maxQ = 456976;
const int P = 26;
string s;
int hashes[maxM][maxS];
int cnt[maxL];
int powers[maxL + 1];
map<int, int> H[maxL][maxQ];
long long res = 0;

void precalc() {
	if (s.size() >= maxL) {
		res = (long long) (s.size() - maxL) * (long long) (s.size() - maxL + 1)
						/ 2LL;
	}

	powers[0] = 1;

	for (int i = 1; i <= maxL; ++i) {
		powers[i] = powers[i - 1] * P;
	}

	for (int length = 1; length <= maxL && length <= s.size(); ++length) {
		int left = 0;
		int right = s.size() - length;
		int hash = 0;

		if (length >= maxM) {
			for (int i = left + maxB; i < left + length; ++i) {
				hash = hash * P + (s[i] - 'a');
			}
		} else {
			for (int i = left; i < left + length; ++i) {
				hash = hash * P + (s[i] - 'a');
			}
		}

		int hashB = 0;

		if (length >= maxM) {
			for (int i = left; i < left + maxB; ++i) {
				hashB = hashB * P + (s[i] - 'a');
			}
		}

		for (int i = left; i <= right; ++i) {
			if (length >= maxM) {
				++H[length - 1][hashB][hash];

				if (H[length - 1][hashB][hash] == 1) {
					++res;
				}
			} else {
				++hashes[length - 1][hash];

				if (hashes[length - 1][hash] == 1) {
					++cnt[length - 1];
					++res;
				}
			}

			hash = hash * P + (s[i + length] - 'a');

			if (length >= maxM) {
				hash -= (s[i + maxB] - 'a') * powers[length - maxB];
			} else {
				hash -= (s[i] - 'a') * powers[length];
			}

			if (length >= maxM) {
				hashB = hashB * P + (s[i + maxB] - 'a');
				hashB -= (s[i] - 'a') * powers[maxB];
			}
		}
	}
}

void update(int position, char value) {
	for (int iters = 0; iters < 2; ++iters) {
		for (int length = 1; length <= maxL; ++length) {
			int left = max(0, position - length + 1);
			int right = min(position, (int) (s.size() - length));

			if (left > right) {
				continue;
			}

			int hash = 0;

			if (length >= maxM) {
				for (int i = left + maxB; i < left + length; ++i) {
					hash = hash * P + (s[i] - 'a');
				}
			} else {
				for (int i = left; i < left + length; ++i) {
					hash = hash * P + (s[i] - 'a');
				}
			}

			int hashB = 0;

			if (length >= maxM) {
				for (int i = left; i < left + maxB; ++i) {
					hashB = hashB * P + (s[i] - 'a');
				}
			}

			for (int i = left; i <= right; ++i) {
				if (iters) {
					if (length >= maxM) {
						++H[length - 1][hashB][hash];

						if (H[length - 1][hashB][hash] == 1) {
							++res;
						}
					} else {
						++hashes[length - 1][hash];

						if (hashes[length - 1][hash] == 1) {
							++cnt[length - 1];
							++res;
						}
					}
				} else {
					if (length >= maxM) {
						--H[length - 1][hashB][hash];
					} else {
						--hashes[length - 1][hash];
					}
				}

				if (length >= maxM) {
					if (H[length - 1][hashB][hash] == 0) {
						H[length - 1][hashB].erase(hash);
						--res;
					}
				} else {
					if (hashes[length - 1][hash] == 0) {
						--cnt[length - 1];
						--res;
					}
				}

				hash = hash * P + (s[i + length] - 'a');

				if (length >= maxM) {
					hash -= (s[i + maxB] - 'a') * powers[length - maxB];
				} else {
					hash -= (s[i] - 'a') * powers[length];
				}

				if (length >= maxM) {
					hashB = hashB * P + (s[i + maxB] - 'a');
					hashB -= (s[i] - 'a') * powers[maxB];
				}
			}
		}

		s[position] = value;
	}
}

int main() {
	int n, m;
	scanf("%d%d", &n, &m);
	assert(4 <= n && n <= 75000);
	assert(4 <= m && m <= 75000);
	cin >> s;
	precalc();

	for (int i = 0; i < m; ++i) {
		int position;
		char new_value;
		scanf("%d %c", &position, &new_value);
		update(position - 1, new_value);
		printf("%lld\n", res);
	}

	return 0;
}
