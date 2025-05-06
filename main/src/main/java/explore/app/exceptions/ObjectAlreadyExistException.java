package explore.app.exceptions;

public class ObjectAlreadyExistException extends RuntimeException {

    public ObjectAlreadyExistException(String message) {
        super(message);
    }
}