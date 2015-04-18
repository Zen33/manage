package fitfame.common.exception;

import org.springframework.dao.IncorrectResultSizeDataAccessException;

@SuppressWarnings("serial")
public class NonUniqueResultException extends IncorrectResultSizeDataAccessException {

	public NonUniqueResultException(int actualSize) {
		super(1, actualSize);
	}

	public NonUniqueResultException(String msg, int actualSize) {
		super(msg, 1, actualSize);
	}
}
