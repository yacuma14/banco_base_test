package banco.test.exception;

public class NotFoundPagoException extends RuntimeException {

	  private static final long serialVersionUID = 1L;

	  public NotFoundPagoException(String msg) {
	    super(msg);
	  }
	}