
public class MalformedDataException extends Exception {
	int lineNumber;

	public MalformedDataException() {
		// TODO Auto-generated constructor stub
	}

	public MalformedDataException(String message) {
		super(message);
		// TODO Auto-generated constructor stub
	}

	public MalformedDataException(Throwable cause) {
		super(cause);
		// TODO Auto-generated constructor stub
	}
	
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

	public MalformedDataException(String message, Throwable cause) {
		super(message, cause);
		// TODO Auto-generated constructor stub
	}

	public MalformedDataException(String message, Throwable cause, boolean enableSuppression,
			boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
		// TODO Auto-generated constructor stub
	}

}
