/**
 * Exception used to convert SQL exceptions into a different package
 */
public class DataAccessException extends Exception {
    DataAccessException(String message) {
        super(message);
    }
}
