package exception;

public class TestException extends RuntimeException {

  private static final long serialVersionUID = 1L;

  public TestException(String message) {
    super(message);
  }

  public TestException(String message, Throwable t) {
    super(message, t);
  }
}
