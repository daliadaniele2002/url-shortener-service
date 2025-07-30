package daniele.dalia.urlshortenerservice.decodeservice.exception;

public class ExpiredException extends RuntimeException {
    public ExpiredException(String message) {
        super(message);
    }
}
