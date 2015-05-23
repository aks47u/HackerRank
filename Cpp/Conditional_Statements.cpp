#include <iostream>
#include <vector>

using namespace std;

int main() {
	vector < string > arr;
	arr.push_back("zero");
	arr.push_back("one");
	arr.push_back("two");
	arr.push_back("three");
	arr.push_back("four");
	arr.push_back("five");
	arr.push_back("six");
	arr.push_back("seven");
	arr.push_back("eight");
	arr.push_back("nine");
	int n;
	cin >> n;

	if (n > 9) {
		cout << "Greater than 9" << endl;
	} else {
		cout << arr[n] << endl;
	}

	return 0;
}
