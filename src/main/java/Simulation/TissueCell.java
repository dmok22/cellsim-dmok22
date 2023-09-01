package Simulation;

import Util.Calculator;
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
        int deadCellCount = 0;

        int centerX = getX();
        int centerY = getY();

        for (int xOffset = -1; xOffset <= 1; xOffset++) {
            for (int yOffset = -1; yOffset <= 1; yOffset++) {
                if (xOffset == 0 && yOffset == 0) {
                    continue; // Skip the cell itself
                }

                int neighborX = centerX + xOffset;
                int neighborY = centerY + yOffset;

                int neighborIndex = Calculator.indexFromCoord(neighborX, neighborY);

                if (neighborIndex >= 0 && neighborIndex < neighbors.size() && neighbors.get(neighborIndex) instanceof DeadCell) {
                    deadCellCount++;
                }
            }
        }

        if (deadCellCount > 0 && random.nextDouble() < 0.7) {
            while (true) {
                int xOffset = random.nextInt(3) - 1; // Random offset (-1, 0, or 1)
                int yOffset = random.nextInt(3) - 1;

                if (xOffset == 0 && yOffset == 0) {
                    continue; // Skip the cell itself
                }

                int newX = centerX + xOffset;
                int newY = centerY + yOffset;

                int newNeighborIndex = Calculator.indexFromCoord(newX, newY);

                if (newNeighborIndex >= 0 && newNeighborIndex < neighbors.size() && neighbors.get(newNeighborIndex) instanceof DeadCell) {
                    neighbors.set(newNeighborIndex, new TissueCell(newX, newY));
                    break;
                }
            }
        }
    }

}