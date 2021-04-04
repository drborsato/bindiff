package br.com.drborsato.bindiff.exception;

import br.com.drborsato.bindiff.model.FileId;

public class BinFileConflictException extends RuntimeException {

    public BinFileConflictException(FileId fileId) {
        super(String.format("File id %o already exist at %s", fileId.getId(), fileId.getSide()));
    }
}
