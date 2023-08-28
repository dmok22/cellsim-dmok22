package Simulation;

import Util.Pair;

import java.util.ArrayList;
import java.util.Random;

/**
 * The immune cell! It kills cancer, and has a chance to attack multiple cancer cells per turn!
 */

public class ImmuneCell extends Cell{

    /**
     * The logic object expects a constructor that takes a coordinate stored as a pair
     * See the Util folder and Pair.java to learn about the implementation of this
     * @param coords
     */
    public ImmuneCell(Pair coords) {
        this(coords.getX(), coords.getY());
    }
    public ImmuneCell(int x, int y){
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
        for (Cell neighbor : neighbors) {
            System.out.println("Checking neighbor: " + neighbor.getClass().getSimpleName());
            if (random.nextDouble() < ATTACK_CHANCE) {
                System.out.println("Attacking CancerCell!");
                int x = neighbor.getX();
                int y = neighbor.getY();
                neighbors.set(neighbors.indexOf(neighbor), new DeadCell(x, y));
                // Optional: 50% chance of attacking again
                if (random.nextDouble() < 0.5) {
                    System.out.println("Attacking again!");
                    interactNeighbors(neighbors); // Recursive call for multiple attacks
                }
                break; // Attack only one CancerCell in this iteration
            }
        }
    }
}