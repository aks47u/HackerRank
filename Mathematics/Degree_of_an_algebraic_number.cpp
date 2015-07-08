#include <stdio.h>
 
#define N 10000000
#define ll long long
 
int pf[N + 1];
int pt[N + 1];
int ps[N + 1];
int vs[11111];
ll sigs[11111];
 
int main() {
    for (int i = 0; i <= N; i++) {
        pf[i] = i;
    }
 
    for (int i = 2; i <= N; i++) {
        if (pf[i] == i) {
            for (int j = i; j <= N; j += i) {
                pf[j] = i;
            }
        }
    }
 
    int z;
    scanf("%d", &z);
 
    for (int cas = 1; cas <= z; cas++) {
        int n;
        scanf("%d", &n);
        int k = 0;
 
        for (int i = 0; i < n; i++) {
            int v;
            scanf("%d", &v);
            vs[i] = v;
 
            while (v > 1) {
                int p = pf[v];
                int e = 0;
 
                while (v % p == 0) {
                    v /= p, e ^= 1;
                }
 
                if (e && pt[p] != cas) {
                    pt[p] = cas;
                    sigs[ps[p] = k++] = 0;
                }
            }
        }
 
        for (int i = 0; i < n; i++) {
            int v = vs[i];
 
            while (v > 1) {
                int p = pf[v];
                int e = 0;
 
                while (v % p == 0) {
                    v /= p, e ^= 1;
                }
 
                if (e) {
                    sigs[ps[p]] |= 1LL << i;
                }
            }
        }
 
        int ct = 0;
 
        for (int i = k - 1; i >= 0; i--) {
            int m = 0;
 
            for (int j = 0; j <= i; j++) {
                if (sigs[m] < sigs[j]) {
                    m = j;
                }
            }
 
            ll v = sigs[m];
 
            if (v == 0) {
                break;
            }
 
            ct++;
            sigs[m] = sigs[i];
            sigs[i] = v;
            v |= v >> 1;
            v |= v >> 2;
            v |= v >> 4;
            v |= v >> 8;
            v |= v >> 16;
            v |= v >> 32;
            v ^= v >> 1;
 
            for (int j = 0; j < i; j++) {
                if (sigs[j] & v) {
                    sigs[j] ^= sigs[i];
                }
            }
        }
 
        printf("%lld\n", 1LL << ct);
    }
}
