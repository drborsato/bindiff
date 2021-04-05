package br.com.drborsato.bindiff.model;

public class Offset {

    private int position;
    private byte left;
    private byte right;

    public Offset() {
    }

    public Offset(int position, byte left, byte right) {
        this.position = position;
        this.left = left;
        this.right = right;
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    public byte getLeft() {
        return left;
    }

    public void setLeft(byte left) {
        this.left = left;
    }

    public byte getRight() {
        return right;
    }

    public void setRight(byte right) {
        this.right = right;
    }
}
