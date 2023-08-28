package Simulation;


import Util.Pair;
import java.util.ArrayList;

/**
 * This cell is dead and does nothing
 */
public class DeadCell extends Cell{

    /**
     * The logic object expects a constructor that takes a coordinate stored as a pair
     * See the Util folder and Pair.java to learn about the implementation of this
     * @param coords
     */
    public DeadCell(Pair coords){
        this(coords.getX(), coords.getY());
    }
    public DeadCell(int x, int y){
        super(x, y, 0, 0, "DeadCell");
    }

    @Override
    public void interactNeighbors(ArrayList<Cell> neighbors) {
        // DeadCell does nothing in its interaction
    }

}
