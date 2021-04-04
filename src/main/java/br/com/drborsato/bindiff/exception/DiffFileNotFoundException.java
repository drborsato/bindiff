package br.com.drborsato.bindiff.exception;

public class DiffFileNotFoundException extends RuntimeException {

    public DiffFileNotFoundException(String message) {
        super(message);
    }
}
