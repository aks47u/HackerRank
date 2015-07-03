import java.util.Scanner;
 
public class Number_of_zero_xor_subsets {
    private static int mod = 1000000007;
 
    public static void main(String[] args) {
        Scanner scn = new Scanner(System.in);
        int T = scn.nextInt();
 
        while (T-- > 0) {
            System.out.println(solve(scn.nextLong()));
        }
 
        scn.close();
    }
 
    private static long exp(long b, long e, long m) {
        long r = 1;
 
        while (e > 0) {
            if ((e & 1) == 1) {
                r = (r * b) % m;
            }
 
            b = (b * b) % m;
            e >>= 1;
        }
 
        return r;
    }
 
    private static long solve(long N) {
        long total = exp(2, (exp(2, N, mod - 1) - N % (mod - 1) + mod - 1)
                % (mod - 1), mod);
 
        return total;
    }
}
