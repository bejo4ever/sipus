package javadevelopment.testing;

public class Client1 {

	private Client1() {}
	
	/**
	 * @param args not used
	 */
	public static void main(String[] args) {
		final SimpleClient client = new SimpleClient(
				Constants.AMIR_NAME,
				Constants.AMIR_PASS, 
				Constants.AMIR_COLOR, 
				Constants.AMIR_NAME);
		client.run();
	}

}
