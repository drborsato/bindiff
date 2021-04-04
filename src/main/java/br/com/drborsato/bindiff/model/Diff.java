package br.com.drborsato.bindiff.model;

import java.util.List;

public class Diff {
    private boolean isEqual;
    private List<Offset> offsetDiff;

    public Diff(boolean isEqual, List<Offset> offsetDiff) {
        this.isEqual = isEqual;
        this.offsetDiff = offsetDiff;
    }

    public boolean isEqual() {
        return isEqual;
    }

    public void setEqual(boolean equal) {
        isEqual = equal;
    }

    public List<Offset> getOffsetDiff() {
        return offsetDiff;
    }

    public void setOffsetDiff(List<Offset> offsetDiff) {
        this.offsetDiff = offsetDiff;
    }
}
