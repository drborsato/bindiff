package br.com.drborsato.bindiff.exception;

public class BinDataConverterException extends RuntimeException {

    public BinDataConverterException(String data) {
        super(String.format("Data %s is not at Base64", data));
    }
}
