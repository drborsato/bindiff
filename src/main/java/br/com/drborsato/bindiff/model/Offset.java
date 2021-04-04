package br.com.drborsato.bindiff.model;

public class Offset {
    private int position;
    private Side side;
    private byte value;

    public Offset(int position, Side side, byte value) {
        this.position = position;
        this.side = side;
        this.value = value;
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    public Side getSide() {
        return side;
    }

    public void setSide(Side side) {
        this.side = side;
    }

    public byte getValue() {
        return value;
    }

    public void setValue(byte value) {
        this.value = value;
    }
}
