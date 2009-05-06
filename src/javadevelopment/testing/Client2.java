package javadevelopment.testing;

public class Client2 {

	private Client2() { }
	
	/**
	 * @param args not used
	 */
	public static void main(String[] args) {
		final SimpleClient client = new SimpleClient(
				Constants.CLIENT2_NAME,
				Constants.CLIENT2_PASSWORD, 
				Constants.CLIENT2_COLOR, 
				Constants.AMIR_NAME);
		client.run();
	}

}
