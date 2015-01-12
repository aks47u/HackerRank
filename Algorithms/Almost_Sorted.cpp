#include <iostream>
#include <cstring>
#include <vector>
#include <algorithm>
using namespace std;

typedef long long LL;
int n;
vector<LL> a, b;

void reverse(int ll, int rr) {
	while (ll < rr) {
		LL temp = a[ll];
		a[ll] = a[rr];
		a[rr] = temp;
		++ll;
		--rr;
	}
}

void print() {
	for (int i = 0; i < n; ++i) {
		cout << a[i] << " ";
	}
}

int main() {
	cin >> n;
	LL d;
	
	for (int i = 0; i < n; ++i) {
		cin >> d;
		a.push_back(d);
		b.push_back(d);
	}
	
	sort(b.begin(), b.end());
	bool canSwap = true;
	int p1 = -1, p2 = -1;
	
	for (int i = 0; i < n; ++i) {
		if (a[i] == b[i]) {
			continue;
		}
		
		if (p1 == -1) {
			p1 = i;
			continue;
		}
		
		if (p2 == -1) {
			p2 = i;
			continue;
		}
		
		canSwap = false;
		break;
	}

	if (p1 == -1) {
		cout << "yes" << endl;
		
		return 0;
	}

	if (canSwap && p1 != -1 && p2 != -1 && b[p1] == a[p2] && b[p2] == a[p1]) {
		cout << "yes" << endl;
		cout << "swap " << p1 + 1 << " " << p2 + 1 << endl;
		
		return 0;
	}

	bool reversed = false, canReverse = true;
	int i = 0;
	
	while (i < n - 1) {
		if (a[i] < a[i + 1]) {
			++i;
			continue;
		}
		
		if (reversed) {
			canReverse = false;
			break;
		}
		
		int j = i;
		
		while (j < n - 1 && a[j] > a[j + 1]) {
			++j;
		}
		
		p1 = i;
		p2 = j;
		reverse(i, j);
		reversed = true;
	}

	if (canReverse) {
		cout << "yes" << endl;
		cout << "reverse " << p1 + 1 << " " << p2 + 1 << endl;
	} else {
		cout << "no" << endl;
	}

	return 0;
}
