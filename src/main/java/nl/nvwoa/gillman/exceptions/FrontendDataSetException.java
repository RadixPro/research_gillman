package nl.nvwoa.gillman.exceptions;

/**
 * Exception if no or invalid DataSet is used.
 */
public class FrontendDataSetException extends RuntimeException {
    public FrontendDataSetException(final String message) {
        super(message);
    }
}
