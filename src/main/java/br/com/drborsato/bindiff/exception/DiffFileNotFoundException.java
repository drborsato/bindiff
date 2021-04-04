package br.com.drborsato.bindiff.exception;

import br.com.drborsato.bindiff.model.FileId;

public class DiffFileNotFoundException extends RuntimeException {

    public DiffFileNotFoundException(FileId fileId) {
        super(String.format("File id %o not found at %s", fileId.getId(), fileId.getSide()));
    }
}
