#include <iostream>

using namespace std;

class Student {
public:
	int age, standard;
	string first_name, last_name;

	void set_age(int a) {
		age = a;
	}

	void set_first_name(string a) {
		first_name = a;
	}

	void set_last_name(string a) {
		last_name = a;
	}

	void set_standard(int a) {
		standard = a;
	}

	int get_age() {
		return age;
	}

	string get_first_name() {
		return first_name;
	}

	string get_last_name() {
		return last_name;
	}

	int get_standard() {
		return standard;
	}

	string t_string() {
		string s = to_string(age) + "," + first_name + "," + last_name + ","
				+ to_string(standard);

		return s;
	}
};

int main() {
	int age, standard;
	string first_name, last_name;
	cin >> age >> first_name >> last_name >> standard;
	Student st;
	st.set_age(age);
	st.set_standard(standard);
	st.set_first_name(first_name);
	st.set_last_name(last_name);
	cout << st.get_age() << endl;
	cout << st.get_last_name() << ", " << st.get_first_name() << endl;
	cout << st.get_standard() << endl;
	cout << endl;
	cout << st.t_string();

	return 0;
}
