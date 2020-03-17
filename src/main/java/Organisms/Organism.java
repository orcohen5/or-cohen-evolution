package Organisms;

import Utilities.Randomizer;

public abstract class Organism extends Thread {
    private final int STRENGTH = 1;
    private final int INTELLIGENCE = 2;
    private final int TECHNOLOGICAL_MEANS = 3;
    private final int NUMBER_OF_PROPERTIES = 3;
    private final int INCREASE = 1;
    private final int NUMBER_OF_OPTIONS = 5;
    private double strength;
    private double intelligence;
    private double technologicalMeans;
    private double mutation;
    private long balance;
    private int multiplication;
    private String name;
    private boolean isAlive;

    public Organism(String name) {
        this.strength = 1.0;
        this.intelligence = 1.0;
        this.technologicalMeans = 1.0;
        this.mutation = 1.5;
        this.balance = 1;
        this.multiplication = 3;
        this.name = name;
        this.isAlive = true;
    }

    public double getStrength() {
        return strength;
    }

    public void setStrength(double strength) {
        this.strength = strength;
    }

    public double getIntelligence() {
        return intelligence;
    }

    public void setIntelligence(double intelligence) {
        this.intelligence = intelligence;
    }

    public double getTechnologicalMeans() {
        return technologicalMeans;
    }

    public void setTechnologicalMeans(double technologicalMeans) {
        this.technologicalMeans = technologicalMeans;
    }

    public long getBalance() {
        return balance;
    }

    public void setBalance(long balance) {
        this.balance = balance;
    }

    public String getOrganismName() {
        return name;
    }

    public boolean isOrganismAlive() {
        return isAlive;
    }

    public void setOrganismAlive(boolean alive) {
        isAlive = alive;
    }

    public double getSumOfProperties() {
        return strength + intelligence + technologicalMeans;
    }

    public abstract String getType();

    public abstract void increaseProperties();

    public void run() {
        increaseConstantly();
        increaseOptionally();
        increaseBalance();
    }

    public String toString() {
        String organismData = name + " -> [" +
                "Strength = " + strength + ", " +
                "Intelligence = " + intelligence + ", " +
                "Technological Means = " + technologicalMeans + ", " +
                "Balance = " + balance + "] " +
                "Thread[" + currentThread().getName() + "]" +
                "[" + getType() + "]";
        return organismData;
    }

    private void increaseConstantly() {
        int propertyToIncrease = Randomizer.getRandomNumber(NUMBER_OF_PROPERTIES);

        if (propertyToIncrease == STRENGTH) {
            strength *= mutation;
        }
        else if (propertyToIncrease == INTELLIGENCE) {
            intelligence *= mutation;
        }
        else if (propertyToIncrease == TECHNOLOGICAL_MEANS) {
            technologicalMeans *= mutation;
        }
    }

    private void increaseOptionally() {
        int option = Randomizer.getRandomNumber(NUMBER_OF_OPTIONS);

        if (option == INCREASE) {
            increaseProperties();
        }
    }

    private void increaseBalance() {
        balance *= multiplication;
    }
}
