package com.aicreator.directorai.exception;

/**
 * Thrown when a requested resource cannot be found (e.g., no
 * ProductionJob exists for a given id). Translated to an HTTP 404
 * response by {@link GlobalExceptionHandler}.
 */
public class ResourceNotFoundException extends RuntimeException {

    public ResourceNotFoundException(String message) {
        super(message);
    }
}
