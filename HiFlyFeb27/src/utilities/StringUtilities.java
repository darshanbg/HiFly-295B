package utilities;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

public class StringUtilities {
	public static String convertToString(InputStream is) {
		BufferedReader br = new BufferedReader(new InputStreamReader(is));
		StringBuilder builder = new StringBuilder();
		String output = null;
		try {
			while ((output = br.readLine()) != null) {
				builder.append(output);
			}
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		return builder.toString();
	}
}
