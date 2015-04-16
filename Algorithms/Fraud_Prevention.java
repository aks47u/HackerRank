import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.TreeSet;
import java.util.regex.Pattern;

public class Fraud_Prevention {
	private static HashMap<String, String> STATE_NORMS = new HashMap<String, String>();
	private static Pattern COMMA_PAT = Pattern.compile(",");
	private static final String SEP = "_";

	static {
		STATE_NORMS.put("illinois", "il");
		STATE_NORMS.put("new york", "ny");
		STATE_NORMS.put("california", "ca");
	}

	public static void main(String[] args) throws Exception {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		PrintWriter pw = new PrintWriter(new OutputStreamWriter(System.out));
		int n = Integer.parseInt(br.readLine());
		TreeSet<Long> frauds = new TreeSet<Long>();
		FraudChecker[] checkers = { new EmailDealIDFraudChecker(),
				new AddressDealIDFraudChecker() };

		for (int i = 0; i < n; i++) {
			Order order = new Order(br.readLine());
			long fraud = -1;

			for (FraudChecker fc : checkers) {
				fraud = fc.getMatchingOrderID(order);

				if (fraud != -1) {
					break;
				}
			}

			if (fraud != -1) {
				frauds.add(fraud);
				frauds.add(order.orderID);
			}
		}

		boolean first = true;

		for (Long fraud : frauds) {
			if (first) {
				first = false;
			} else {
				pw.print(",");
			}

			pw.print(fraud);
		}

		pw.println();
		pw.flush();
	}

	private static String normalizeCity(String city) {
		return city.toLowerCase();
	}

	private static String normalizeStreet(String street) {
		street = street.toLowerCase();

		if (street.contains("street")) {
			street = street.replaceAll("street", "st.");
		}

		if (street.contains("road")) {
			street = street.replaceAll("road", "rd.");
		}

		return street;
	}

	private static String normalizeState(String state) {
		state = state.toLowerCase();

		return STATE_NORMS.containsKey(state) ? STATE_NORMS.get(state) : state;
	}

	private static String normalizeEmail(String email) {
		email = email.toLowerCase();
		int at = email.indexOf('@');

		if (at == -1) {
			return email;
		}

		String name = email.substring(0, at);
		int plus = name.indexOf('+');

		if (plus != -1) {
			name = name.substring(0, plus);
		}

		if (name.indexOf('.') != -1) {
			name = name.replaceAll("\\.+", "");
		}

		return name + email.substring(at);
	}

	public static class Order {
		long orderID, dealID;
		String email, street, city, state, zip, cc;

		public Order(String in) throws Exception {
			if (in == null) {
				throw new Exception("Bad input");
			}

			String[] parts = COMMA_PAT.split(in, 8);

			if (parts.length < 8) {
				throw new Exception("Bad input:" + in);
			}

			orderID = Long.parseLong(parts[0]);
			dealID = Long.parseLong(parts[1]);
			email = parts[2];
			street = parts[3];
			city = parts[4];
			state = parts[5];
			zip = parts[6];
			cc = parts[7];
		}
	}

	public interface FraudChecker {
		public long getMatchingOrderID(Order order);
	}

	public static abstract class AbstractKeyedFraudChecker implements
	FraudChecker {
		private HashMap<String, Long> keyMap;

		public AbstractKeyedFraudChecker() {
			keyMap = new HashMap<String, Long>();
		}

		public long getMatchingOrderID(Order order) {
			String key = getOrderKey(order);
			Long existing = keyMap.get(key);

			if (existing != null) {
				return existing;
			}

			keyMap.put(key, order.orderID);

			return -1;
		}

		public abstract String getOrderKey(Order order);
	}

	public static class EmailDealIDFraudChecker extends
	AbstractKeyedFraudChecker {
		@Override
		public String getOrderKey(Order order) {
			return order.dealID + SEP + normalizeEmail(order.email);
		}
	}

	public static class AddressDealIDFraudChecker extends
	AbstractKeyedFraudChecker {
		@Override
		public String getOrderKey(Order order) {
			StringBuilder sb = new StringBuilder();
			sb.append(order.dealID).append(SEP);
			sb.append(normalizeStreet(order.street)).append(SEP);
			sb.append(normalizeCity(order.city)).append(SEP);
			sb.append(normalizeState(order.state)).append(SEP);
			sb.append(order.zip);

			return sb.toString();
		}
	}
}
