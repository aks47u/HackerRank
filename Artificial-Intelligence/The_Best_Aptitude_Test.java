import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class The_Best_Aptitude_Test {
	public static void main(String[] argv) {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		
		try {
			int t = Integer.parseInt(br.readLine().trim());
			
			for (int i = 0; i < t; i++) {
				int n = Integer.parseInt(br.readLine().trim());
				float[] gpa = new float[n];
				float total = 0.0f;
				String[] words = br.readLine().trim().split(" ");
				
				for (int j = 0; j < n; j++) {
					gpa[j] = Float.parseFloat(words[j]);
					total += gpa[j];
				}
				
				float gpaMean = total / n;
				float[][] at = new float[5][n];
				float[] atMean = new float[5];
				
				for (int j = 0; j < 5; j++) {
					total = 0.0f;
					words = br.readLine().trim().split(" ");
					
					for (int k = 0; k < words.length; k++) {
						at[j][k] = Float.parseFloat(words[k]);
						total += at[j][k];
					}
					
					atMean[j] = total / n;
				}
				
				float[] covar = new float[5];
				
				for (int j = 0; j < 5; j++) {
					total = 0.0f;
					
					for (int k = 0; k < words.length; k++) {
						total += (at[j][k] - atMean[j]) * (gpa[k] - gpaMean);
					}
					
					covar[j] = total / n;
				}
				
				float maxCovar = covar[0];
				int maxCovarInd = 0;
				
				for (int j = 1; j < 5; j++) {
					if (covar[j] > maxCovar) {
						maxCovar = covar[j];
						maxCovarInd = j;
					}
				}
				
				System.out.println((maxCovarInd + 1));
			}
		} catch (NumberFormatException nfe) {
			System.out.println("NumberFormatException message:"
					+ nfe.getMessage());
		} catch (IOException ioe) {
			System.out.println("IOException message:" + ioe.getMessage());
		}
	}
}
