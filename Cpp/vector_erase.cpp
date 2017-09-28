#include <cstdio>
#include <vector>
#include <iostream>
#include <algorithm>
using namespace std;

int main() {
    /* Enter your code here. Read input from STDIN. Print output to STDOUT */ 
    int n,i,b,c,d,size;
    cin>>n;
    vector<int> a;
    for(i=0;i<n;i++)
        {
        int x;
        cin>>x;
        a.push_back(x);
        }
    cin>>b>>c>>d;
    a.erase(a.begin()+b-1);
    a.erase(a.begin()+c-1,a.begin()+d-1);
    
    size=a.size();
    
    cout<<size<<endl;
    for(i=0;i<size;i++)
        cout<<a[i]<<" ";
    return 0;
}
