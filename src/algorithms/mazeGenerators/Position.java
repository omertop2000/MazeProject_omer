package algorithms.mazeGenerators;

import java.io.Serializable;

public class Position implements Serializable {
    private final int x;
    private final int y;

    public Position(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public String toString() {
        return "{" + x + "," + y + "}";
    }

    public int getRowIndex() {
        return x;
    }

    public int getColumnIndex() {
        return y;
    }

}
