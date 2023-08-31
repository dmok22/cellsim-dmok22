package Simulation;


import Util.Pair;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * A tissue cell. It wants to grow, but not as much as cancer. Has a chance to turn a dead
 * cell into a live one every time step
 */

public class TissueCell extends Cell {

    /**
     * The logic object expects a constructor that takes a coordinate stored as a pair
     * See the Util folder and Pair.java to learn about the implementation of this
     *
     * @param coords
     */
    public TissueCell(Pair coords) {
        this(coords.getX(), coords.getY());
    }

    public TissueCell(int x, int y) {
        super(x, y, 0, 1, "TissueCell");
    }

    private static final double GROW_CHANCE = 0.7; // 70% chance of growing
    private Random random = new Random();

    @Override
    public void interactNeighbors(ArrayList<Cell> neighbors) {
        ArrayList<Cell> immediateNeighbors = getImmediateNeighbors(neighbors);

        int deadCellCount = 0;
        for (Cell neighbor : immediateNeighbors) {
            if (neighbor instanceof DeadCell) {
                deadCellCount++;
            }
        }

        if (deadCellCount > 0 && random.nextDouble() < GROW_CHANCE) {
            int index = random.nextInt(immediateNeighbors.size());
            Cell chosenNeighbor = immediateNeighbors.get(index);
            int x = chosenNeighbor.getX();
            int y = chosenNeighbor.getY();
            neighbors.set(neighbors.indexOf(chosenNeighbor), new TissueCell(x, y));
        }
    }
    private ArrayList<Cell> getImmediateNeighbors(ArrayList<Cell> neighbors) {
        ArrayList<Cell> immediateNeighbors = new ArrayList<>();

        int centerX = getX();
        int centerY = getY();

        for (Cell neighbor : neighbors) {
            int neighborX = neighbor.getX();
            int neighborY = neighbor.getY();

            // Check if the neighbor cell is within one cell distance in x and y directions
            if (Math.abs(neighborX - centerX) <= 1 && Math.abs(neighborY - centerY) <= 1) {
                immediateNeighbors.add(neighbor);
            }
        }

        return immediateNeighbors;
    }
}
