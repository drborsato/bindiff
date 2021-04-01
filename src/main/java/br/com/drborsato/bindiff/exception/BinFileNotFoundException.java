package br.com.drborsato.bindiff.exception;

import br.com.drborsato.bindiff.model.FileId;

public class BinFileNotFoundException extends RuntimeException {

    public BinFileNotFoundException(FileId fileId) {
        super(String.format("File id %0 not found at %s",fileId.getId(), fileId.getSide()));
    }
}
