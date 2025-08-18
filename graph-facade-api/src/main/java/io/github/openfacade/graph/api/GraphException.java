package io.github.openfacade.graph.api;

public class GraphException extends RuntimeException {
    public GraphException(String message) {
        super(message);
    }

    public GraphException(String message, Throwable cause) {
        super(message, cause);
    }
}
