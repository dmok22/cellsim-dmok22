package Simulation;


import Util.Pair;
import Util.Calculator;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * A tissue cell. It wants to grow, but not as much as cancer. Has a chance to turn a dead
 * cell into a live one every time step
 */

public class TissueCell extends Cell{

    /**
     * The logic object expects a constructor that takes a coordinate stored as a pair
     * See the Util folder and Pair.java to learn about the implementation of this
     * @param coords
     */
    public TissueCell(Pair coords){

    }
    public TissueCell(int x, int y){
        super(x, y, 0, 1, "TissueCell");
    }
    private static final double GROW_CHANCE = 0.7; // 70% chance of growing
    private Random random = new Random();

    @Override
    public void interactNeighbors(ArrayList<Cell> neighbors) {
        List<Cell> deadNeighbors = new ArrayList<>();
        for (Cell neighbor : neighbors) {
            if (neighbor instanceof DeadCell) {
                deadNeighbors.add(neighbor);
            }
        }

        if (!deadNeighbors.isEmpty() && random.nextDouble() < GROW_CHANCE) {
            int index = random.nextInt(deadNeighbors.size());
            Cell chosenNeighbor = deadNeighbors.get(index);
            int x = chosenNeighbor.getX();
            int y = chosenNeighbor.getY();
            neighbors.set(neighbors.indexOf(chosenNeighbor), new TissueCell(x, y));
        }
    }
}
