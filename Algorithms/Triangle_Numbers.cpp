#include <iostream>
using namespace std;

int main() {
    int t;
    cin >> t;
    
    while (t--) {
		long n;
		cin >> n;
		
		if(n % 2 != 0) {
			cout << 2 << endl;
		} else {
			if((n / 2) % 2 == 0) {
				cout << 3 << endl;
			} else {
				cout << 4 << endl;
			}	    
		}
    }
    
    return 0;
}
