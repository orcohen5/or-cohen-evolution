package evolution.exceptions;

public class CivilWarException extends Exception {

    public CivilWarException(String message) {
        super(message);
    }

    public CivilWarException() {
        super("General Exception : Civil War");
    }
}
