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

    public abstract String getType();

    public abstract void increaseProperties();

    public void run() {
        increaseCyclically();
        increaseOptionally();
        increaseBalance();
    }

    public String toString() {
        String organismData = name + " -> [" +
                "Strength=" + strength + ", " +
                "Intelligence=" + intelligence + ", " +
                "Technological Means=" + technologicalMeans + ", " +
                "Balance=" + balance + "] " +
                "Thread[" + currentThread().getName() + "]" +
                "[" + getType() + "]";
        return organismData;
    }

    private void increaseCyclically() {
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
