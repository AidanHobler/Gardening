package gardening;

import java.io.InputStream;

public class Loader {

	public static InputStream load(String path) {
		
		InputStream input;
		try {
			input = Loader.class.getResourceAsStream(path);
			return input;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		return null;
	}
}
