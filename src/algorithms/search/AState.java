package algorithms.search;
import algorithms.mazeGenerators.Position;

import java.io.Serializable;
import java.util.ArrayList;

public class AState implements Comparable<AState>, Serializable {
    private final int row_pos;
    private final int col_pos;
    private String color;
    transient private ArrayList<AState> neighbours;
    private AState previous;
    private int value;

    public AState(int row_pos,int col_pos){
        if (row_pos < 0 || col_pos < 0)
            throw new IllegalArgumentException();
        this.row_pos = row_pos;
        this.col_pos = col_pos;
        this.color = "white";
        this.value = 0;
        this.previous = null;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public AState getPrevious() {
        return previous;
    }

    public void setPrevious(AState previous) {
        this.previous = previous;
    }

    public int getCol_pos() {
        return col_pos;
    }


    public int getRow_pos() {
        return row_pos;
    }


    public ArrayList<AState> getNeighbours() {
        return neighbours;
    }

    public void setNeighbours(ArrayList<AState> neighbours) {
        this.neighbours = neighbours;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String toString(){
        return "{"+row_pos+","+col_pos+"}";
    }


    @Override
    public int compareTo(AState o) {
        return Integer.compare(this.getValue(), o.getValue());
    }

    public Object getObject() {
        return new Position(row_pos, col_pos);
    }
}
