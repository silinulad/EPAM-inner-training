package exception;

/**
 * Created by Silin on 07.2018.
 */
public class ActionException extends Exception {

	private static final long serialVersionUID = 8776781171609389233L;

	public ActionException() {
		super();
	}

	public ActionException(String message) {
		super(message);
	}

	public ActionException(String message, Throwable cause) {
		super(message, cause);
	}

	public ActionException(Throwable cause) {
		super(cause);
	}

	protected ActionException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}
}
