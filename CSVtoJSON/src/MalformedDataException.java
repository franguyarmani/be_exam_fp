
@SuppressWarnings("serial")
public class MalformedDataException extends Exception {
	int lineNumber;

	public MalformedDataException(String message, int lineNumber) {
		super(message);
		this.lineNumber = lineNumber;
	}

	public int getLineNumber() {
		return lineNumber;
	}

	public void setLineNumber(int lineNumber) {
		this.lineNumber = lineNumber;
	}

}