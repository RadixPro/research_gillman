package nl.nvwoa.gillman.exceptions;

/**
 * Exception if no or invalid DataSet is used.
 */
public class FrontendDatasetException extends RuntimeException {
    public FrontendDatasetException(final String message) {
        super(message);
    }
}
