#include <bits/stdc++.h>

using namespace std;

// Complete the happyLadybugs function below.
string happyLadybugs(string b) 
{  
  int a[26]={0};
  int i,l=0,c=0;
  
  for(i=0;i<b.size();i++)
  {
      if(b[i]=='_')
       c++;
      else
      {
          (a[b[i] - 65])++;
          if((b[i] == b[i+1] && (i<(b.size()-1)))|| (b[i] == b[i-1] && i>0))
             l++;
      }
  }
  if(c > 0)
  {
      for(i=0;i<26;i++)
      {
          if(a[i]==1)
          break;
      }
      if(i<26)
      return "NO";
      else 
      return "YES";
  }
  else
  {
    if(b.size()== 1 && b[0]!='_')
     return "NO";
     else
     {
         if(l == b.size())
         return "YES";
         else
         return "NO";
     }
     
  }

}

int main()
{
    ofstream fout(getenv("OUTPUT_PATH"));

    int g;
    cin >> g;
    cin.ignore(numeric_limits<streamsize>::max(), '\n');

    for (int g_itr = 0; g_itr < g; g_itr++) {
        int n;
        cin >> n;
        cin.ignore(numeric_limits<streamsize>::max(), '\n');

        string b;
        getline(cin, b);

        string result = happyLadybugs(b);

        fout << result << "\n";
    }

    fout.close();

    return 0;
}
