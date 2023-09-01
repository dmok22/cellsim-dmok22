package Simulation;

import java.util.ArrayList;
import java.util.Random;

import Util.Calculator;
import Util.Pair;
/**
 *This is a cancer cell. It is the most complex cell as it can attack tissue or immune cells, or grow into a dead cell.
 * For attacking tissue, it is a 1 hit replace it with a dead cell.
 * Immune cells are cooler. Each hit from a cancer cell lowers its strength by 1. When an immune cell reaches 0 strength
 * it dies!
 *
 * It has a priority of action. If it can grow, it will grow. If it can kill a tissue cell, it will do that. Why?
 * Easiest way to grow is to kill a week tissue cell. If no other option, will attack immune cells. Path of
 * least resistance to growing basically.
 *
 * Growing means turning a dead cell into a CancerCell.
 */

public class CancerCell extends Cell{

    public CancerCell(int x, int y){
        super(x, y, 1, 3, "CancerCell");
    }
    private Random random = new Random();

    private int strength = getStrength();

    public CancerCell(Pair pair) {
        super(pair.getX(), pair.getY(), 1, 3, "CancerCell");
    }


    @Override
    public void interactNeighbors(ArrayList<Cell> neighbors) {
        int deadCellCount = 0;
        int tissueCellCount = 0;
        int immuneCellCount = 0;

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

                if (neighborIndex >= 0 && neighborIndex < neighbors.size()) {
                    Cell neighbor = neighbors.get(neighborIndex);

                    if (neighbor instanceof DeadCell) {
                        deadCellCount++;
                    } else if (neighbor instanceof TissueCell) {
                        tissueCellCount++;
                    } else if (neighbor instanceof ImmuneCell) {
                        immuneCellCount++;
                    }
                }
            }
        }

        if (deadCellCount > 0) {
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
                    neighbors.set(newNeighborIndex, new CancerCell(newX, newY));
                    break;
                }
            }
        } else if (tissueCellCount > immuneCellCount && tissueCellCount > 0) {
            while (true) {
                int xOffset = random.nextInt(3) - 1; // Random offset (-1, 0, or 1)
                int yOffset = random.nextInt(3) - 1;

                if (xOffset == 0 && yOffset == 0) {
                    continue; // Skip the cell itself
                }

                int newX = centerX + xOffset;
                int newY = centerY + yOffset;

                int newNeighborIndex = Calculator.indexFromCoord(newX, newY);

                if (newNeighborIndex >= 0 && newNeighborIndex < neighbors.size() && neighbors.get(newNeighborIndex) instanceof TissueCell) {
                    neighbors.set(newNeighborIndex, new DeadCell(newX, newY));
                    break;
                }
            }
        } else if (immuneCellCount > 0) {
            while (true) {
                int xOffset = random.nextInt(3) - 1; // Random offset (-1, 0, or 1)
                int yOffset = random.nextInt(3) - 1;

                if (xOffset == 0 && yOffset == 0) {
                    continue; // Skip the cell itself
                }

                int newX = centerX + xOffset;
                int newY = centerY + yOffset;

                int newNeighborIndex = Calculator.indexFromCoord(newX, newY);

                if (newNeighborIndex >= 0 && newNeighborIndex < neighbors.size() && neighbors.get(newNeighborIndex) instanceof ImmuneCell) {
                    ImmuneCell immuneNeighbor = (ImmuneCell) neighbors.get(newNeighborIndex);
                    immuneNeighbor.decreaseStrength();

                    if (immuneNeighbor.getStrength() <= 0) {
                        neighbors.set(newNeighborIndex, new DeadCell(newX, newY));
                    }
                    break;
                }
            }
        }
    }

}