package errors;

public class NoAsciiFileException extends Exception {
    public NoAsciiFileException(String errorMessage) {
        super(errorMessage);
    }
}
