package Simulation;

import java.util.ArrayList;
import java.util.Random;
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

        for (Cell neighbor : neighbors) {
            if (neighbor instanceof DeadCell) {
                deadCellCount++;
            } else if (neighbor instanceof TissueCell) {
                tissueCellCount++;
            } else if (neighbor instanceof ImmuneCell) {
                immuneCellCount++;
            }
        }

        if (deadCellCount > 0) {
            int index = random.nextInt(neighbors.size());
            Cell chosenNeighbor = neighbors.get(index);
            int x = chosenNeighbor.getX();
            int y = chosenNeighbor.getY();
            neighbors.set(index, new CancerCell(x, y));
        } else if (tissueCellCount > immuneCellCount && tissueCellCount > 0) {
            int index = random.nextInt(neighbors.size());
            Cell chosenNeighbor = neighbors.get(index);
            int x = chosenNeighbor.getX();
            int y = chosenNeighbor.getY();
            neighbors.set(index, new DeadCell(x, y));
        } else if (immuneCellCount > 0) {
            for (Cell neighbor : neighbors) {
                if (neighbor instanceof ImmuneCell) {
                    ((ImmuneCell) neighbor).decreaseStrength();
                    if (((ImmuneCell) neighbor).getStrength() <= 0) {
                        int x = neighbor.getX();
                        int y = neighbor.getY();
                        neighbors.set(neighbors.indexOf(neighbor), new DeadCell(x, y));
                    }
                    break; // Attack only one ImmuneCell in this iteration
                }
            }
        }
    }
}
