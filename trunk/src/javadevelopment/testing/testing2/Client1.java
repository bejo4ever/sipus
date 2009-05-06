package javadevelopment.testing.testing2;

public class Client1 {

	private Client1() {}
	
	/**
	 * @param args not used
	 */
	public static void main(String[] args) {
		final SimpleClient client = new SimpleClient(
				Constants.CLIENT1_NAME,
				Constants.CLIENT1_PASSWORD, 
				Constants.CLIENT1_COLOR, 
				Constants.CLIENT2_NAME);
		client.run();
	}

}
