package Organisms;

import java.util.Random;

public abstract class Organism extends Thread {
    private final int STRENGTH = 1;
    private final int INTELLIGENCE = 2;
    private final int TECHNOLOGICAL_MEANS = 3;
    private final int FIRST_PROPERTY_INDEX = 1;
    private final int LAST_PROPERTY_INDEX = 3;
    private final int YES = 1;
    private final int FIRST_TIME = 1;
    private final int NUMBER_OF_FEW_TIMES = 5;
    private double strength;
    private double intelligence;
    private double technologicalMeans;
    private double mutation;
    private int balance;
    private int multiplication;
    private String name;

    public Organism(String name) {
        this.strength = 1.0;
        this.intelligence = 1.0;
        this.technologicalMeans = 1.0;
        this.mutation = 1.5;
        this.balance = 1;
        this.multiplication = 3;
        this.name = name;
    }

    public double getStrength() {
        return this.strength;
    }

    public void setStrength(double strength) {
        this.strength = strength;
    }

    public double getIntelligence() {
        return this.intelligence;
    }

    public void setIntelligence(double intelligence) {
        this.intelligence = intelligence;
    }

    public double getTechnologicalMeans() {
        return this.technologicalMeans;
    }

    public void setTechnologicalMeans(double technologicalMeans) {
        this.technologicalMeans = technologicalMeans;
    }

    public void run() {
        runOrganismCycle();
    }

    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append(this.name);
        builder.append(" -> ").append("[");
        builder.append("Strength=").append(this.strength).append(", ");
        builder.append("Intelligence=").append(this.intelligence).append(", ");
        builder.append("Technological Means=").append(this.technologicalMeans).append(", ");
        builder.append("Balance=").append(this.balance).append("] ");
        builder.append("Thread[").append(Thread.currentThread().getName()).append("]");
        builder.append("[").append(getType()).append("]");
        String organismData = builder.toString();
        return organismData;
    }

    public abstract String getType();

    public abstract void increaseOrganismProperty();

    private void runOrganismCycle() {
        increaseOnePropertyRandomly();
        increasePropertyIfNeedToIncrease();
        increaseBalance();
    }

    private void increaseOnePropertyRandomly() {
        Random r = new Random();
        int propertyToIncrease = r.nextInt((LAST_PROPERTY_INDEX - FIRST_PROPERTY_INDEX) + 1) + FIRST_PROPERTY_INDEX;

        if (propertyToIncrease == STRENGTH) {
            this.strength = this.strength * this.mutation;
        }
        if (propertyToIncrease == INTELLIGENCE) {
            this.intelligence = this.intelligence * this.mutation;
        }
        if (propertyToIncrease == TECHNOLOGICAL_MEANS) {
            this.technologicalMeans = this.technologicalMeans * this.mutation;
        }
    }

    private void increasePropertyIfNeedToIncrease() {
        Random r = new Random();
        int needToIncrease = r.nextInt((NUMBER_OF_FEW_TIMES - FIRST_TIME) + 1) + FIRST_TIME;

        if (needToIncrease == YES) {
            increaseOrganismProperty();
        }
    }

    private void increaseBalance() {
        this.balance = this.balance * this.multiplication;
    }
}
