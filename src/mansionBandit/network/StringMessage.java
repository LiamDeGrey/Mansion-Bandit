package mansionBandit.network;

/**
 * A StringMessage represents a network sendable String.
 * @author Shreyas
 *
 */
public class StringMessage implements Message {
	private String string;

	public StringMessage(String string) {
		this.string = string;
	}

	public String getString() {
		return string;
	}

}
