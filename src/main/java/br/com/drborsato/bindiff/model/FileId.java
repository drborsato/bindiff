package br.com.drborsato.bindiff.model;

import java.io.Serializable;

public class FileId implements Serializable {
    private long id;
    private Side side;

    public FileId() {
    }

    public FileId(long id, Side side) {
        this.id = id;
        this.side = side;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Side getSide() {
        return side;
    }

    public void setSide(Side side) {
        this.side = side;
    }
}
