package Simulation;

import Util.Pair;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

/**
 * The default, boring cell.
 */

public class Cell {
    /**
     *      * the simulation expects a getter that return the ID that is specifically called getID()
     *      * any other method call will break it. So if you name this "getCellID()" or "getId()" it won't work
     *      * This is why interfaces are useful
     * @return
     */
    private int strength;
    int x;
    int y;
    private int id;
    private Pair pair;
    private int pairx;
    private int pairy;
    private HashMap<String, Double> chemicals;
    private HashSet<String> signalMolecules;
    private String cellType;
    private ArrayList<Cell> neighbors;

    public Cell() {
        this.strength = 0;
        this.x = 0;
        this.y = 0;
        this.id = 0;
        this.chemicals = new HashMap<>();
        this.signalMolecules = new HashSet<>();
        this.neighbors = new ArrayList<>();

    }
    public Cell(int x, int y, int strength) {
        this.x = x;
        this.y = y;
        this.strength = strength;
    }
    public Cell(int x, int y, int strength, int id, String cellType) {
        if (strength > 0){
            this.strength = strength;
        } else{
            this.strength = 0;
        }
        if (x > 0){
            this.x = x;
        } else{
            this.x = 0;
        }
        if (y > 0){
            this.y = y;
        } else{
            this.y = 0;
        }
        if (id > 0){
            this.id = id;
        } else{
            this.id = 0;
        }
        this.cellType = cellType;

    }

    public void addChemical(String chemical, double concentration) {
        chemicals.put(chemical, concentration);
    }

    public void removeChemical(String chemical) {
        chemicals.remove(chemical);
    }

    public double getChemicalConcentration(String chemical) {
        return chemicals.get(chemical);
    }

    public void addSignalMolecule(String molecule) {
        signalMolecules.add(molecule);
    }

    public void removeSignalMolecule(String molecule) {
        signalMolecules.remove(molecule);
    }

    public boolean hasSignalMolecule(String molecule) {
        return signalMolecules.contains(molecule);
    }

    public int getStrength() {

        return strength;
    }

    public void setStrength(int strength) {
        if (strength >= 0) {
            this.strength = strength;
        } else {
            this.strength = 0;
        }
    }

    public int getX() {

        return x;
    }

    public void setX(int x) {
        if (x >= 0) {
            this.x = x;
        } else {
            this.x = 0;
        }
    }
    public int getY() {
        return y;
    }

    public void setY(int y) {
        if (y >= 0) {
            this.y = y;
        } else {
            this.y = 0;
        }
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        if (id >= 0) {
            this.id = id;
        } else {
            this.id = 0;
        }
    }

    public void addNeighbor(Cell neighbor) {
        this.neighbors.add(neighbor);
    }

    public void removeNeighbor(Cell neighbor) {
        this.neighbors.remove(neighbor);
    }

    public ArrayList<Cell> getNeighbors() {
        return this.neighbors;
    }

    public void interactNeighbors(ArrayList<Cell> neighbors) {
        // Placeholder method in the base Cell class
    }


    public int getID(){

        return id;
    }

    public void decreaseStrength() {
        strength--;
    }

}
