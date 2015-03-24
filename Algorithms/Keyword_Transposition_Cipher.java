package Algorithms;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Map;
import java.util.TreeMap;

public class Keyword_Transposition_Cipher {
	private static byte ALPHABET_SIZE = 26;
	private static char ALPHABET_ASCII_OFFSET = 'A';

	public static void main(String[] args) throws IOException {
		StringBuffer sb = new StringBuffer();
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

		for (int N = Integer.parseInt(br.readLine()); N > 0; --N) {
			char[] keyword = br.readLine().toCharArray();
			char[] ciphertext = br.readLine().toCharArray();
			byte numChars = 0;
			Map<Byte, Byte> map = new TreeMap<Byte, Byte>();

			for (char c : keyword) {
				byte b = (byte) (c - ALPHABET_ASCII_OFFSET);

				if (!map.containsKey(b)) {
					map.put(b, numChars++);
				}
			}

			byte subLength = (byte) (ALPHABET_SIZE - numChars);
			byte[] subAlphabet = new byte[subLength];
			byte i = 0, k = 0;

			for (byte b : map.keySet()) {
				while (k < b) {
					subAlphabet[i++] = k++;
				}

				++k;
			}

			while (i < subLength) {
				subAlphabet[i++] = k++;
			}

			char j = ALPHABET_ASCII_OFFSET;
			char[] alphabet = new char[ALPHABET_SIZE];

			for (byte b : map.keySet()) {
				alphabet[b] = j++;

				for (i = map.get(b); i < subLength; i += numChars) {
					alphabet[subAlphabet[i]] = j++;
				}
			}

			for (char c : ciphertext) {
				sb.append(c == ' ' ? ' ' : alphabet[c - ALPHABET_ASCII_OFFSET]);
			}

			sb.append("\n");
		}

		System.out.println(sb);
	}
}
