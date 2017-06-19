package exception;

public class SwitchException extends Exception {

    /**
     * Create a new exception
     */
    public SwitchException() {
        super("Błąd switcha");
    }


    /**
     * Create a new exception
     *
     * @param message String
     */
    public SwitchException(String message) {
        super(message);
    }
}
