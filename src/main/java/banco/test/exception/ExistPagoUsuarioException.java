package banco.test.exception;

public class ExistPagoUsuarioException extends RuntimeException {

	  private static final long serialVersionUID = 1L;

	  public ExistPagoUsuarioException(String msg) {
	    super(msg);
	  }
	}