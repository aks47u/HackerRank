#include<iostream>
#include<set>
#include<math.h>
#define G(i,j) g[i][j]
using namespace std;int s(int r,int c){return(r/3)*3+(c/3);}bool v(int G(9,9),int r,int c){for(int i=0;i<9;i++){if(G(r,i)==G(r,c)&&i!=c)return false;if(G(i,c)==G(r,c)&&i!=r)return false;}for(int i=0;i<9;i++){for(int j=0;j<9;j++){if(s(r,c)==s(i,j)&&r!=i&&c!=j&&G(i,j)==G(r,c))return false;}}return true;}void v(int g[9][9],set<int>y){int f=1;for(int z=0;z<81;){int r=z/9;int c=fmod(z,9);if(y.count(z)>0){z+=1*f;}else{G(r,c)++;if(G(r,c)==10){G(r,c)=0;f=-1;z--;}if(v(g,r,c)){f=1;z++;}}}for(int i=0;i<9;i++){for(int j=0;j<9;j++){cout<<G(i,j)<<" ";}cout<<endl;}}int main(){int n,G(9,9);set<int>y;cin>>n;for(int i=0;i<n;i++){for(int j=0;j<9;j++){for(int k=0;k<9;k++){G(j,k)=0;cin>>G(j,k);if(G(j,k)!=0)y.insert(j*9+k);}}v(g,y);}return 0;}
