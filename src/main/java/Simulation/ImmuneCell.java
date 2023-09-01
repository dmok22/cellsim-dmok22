package Simulation;

import Util.Calculator;
import Util.Pair;

import java.util.ArrayList;
import java.util.Random;

/**
 * The immune cell! It kills cancer, and has a chance to attack multiple cancer cells per turn!
 */

public class ImmuneCell extends Cell {

    /**
     * The logic object expects a constructor that takes a coordinate stored as a pair
     * See the Util folder and Pair.java to learn about the implementation of this
     *
     * @param coords
     */
    public ImmuneCell(Pair coords) {
        this(coords.getX(), coords.getY());
    }

    public ImmuneCell(int x, int y) {
        super(x, y, 3, 4, "ImmuneCell");
    }

    private static final double ATTACK_CHANCE = 1.0; // 100% chance of attacking
    private Random random = new Random();
    private int strength = 2; // Initial strength


    public int getStrength() {
        return strength;
    }

    public void decreaseStrength() {
        strength--;
    }

    @Override
    public void interactNeighbors(ArrayList<Cell> neighbors) {
        int cancerCellCount = 0;

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

                if (neighborIndex >= 0 && neighborIndex < neighbors.size() && neighbors.get(neighborIndex) instanceof CancerCell) {
                    cancerCellCount++;
                }
            }
        }

        if (cancerCellCount > 0) {
            while (true) {
                int xOffset = random.nextInt(3) - 1; // Random offset (-1, 0, or 1)
                int yOffset = random.nextInt(3) - 1;

                if (xOffset == 0 && yOffset == 0) {
                    continue; // Skip the cell itself
                }

                int newX = centerX + xOffset;
                int newY = centerY + yOffset;

                int newNeighborIndex = Calculator.indexFromCoord(newX, newY);

                if (newNeighborIndex >= 0 && newNeighborIndex < neighbors.size() && neighbors.get(newNeighborIndex) instanceof CancerCell) {
                    neighbors.set(newNeighborIndex, new DeadCell(newX, newY));

                    // Optional: 50% chance of attacking again
                    if (random.nextDouble() <= 0.5) {
                        interactNeighbors(neighbors); // Recursive call for multiple attacks
                    }
                    break;
                }
            }
        }
    }

}