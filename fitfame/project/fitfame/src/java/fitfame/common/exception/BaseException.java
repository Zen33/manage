package fitfame.common.exception;

/**
 * 基础异常类
 * @author ahmanz
 *
 */
@SuppressWarnings("serial")
public class BaseException extends RuntimeException {

	public BaseException() {
		super();
	}

	public BaseException(String message, Throwable cause) {
		super(message, cause);
	}

	public BaseException(String message) {
		super(message);
	}

	public BaseException(Throwable cause) {
		super(cause);
	}

}