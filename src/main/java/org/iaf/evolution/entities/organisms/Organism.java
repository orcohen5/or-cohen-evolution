package org.iaf.evolution.entities.organisms;

import org.iaf.evolution.utilities.LoggerUtil;
import org.iaf.evolution.utilities.Randomizer;

public abstract class Organism extends Thread {
    private final int STRENGTH = 1;
    private final int INTELLIGENCE = 2;
    private final int TECHNOLOGICAL_MEANS = 3;
    private final int NUMBER_OF_PROPERTIES = 3;
    private final int INCREASE = 1;
    private final int NUMBER_OF_OPTIONS = 5;
    private final int DIFFERENCE_BETWEEN_PROPERTIES = 5;
    private final int NOT_FIGHTING_YET = -1;
    private final int DRAW = 0;
    private final int DEFENDER_WIN = 1;
    private final int ATTACKER_WIN = 2;
    private double strength;
    private double intelligence;
    private double technologicalMeans;
    private double mutation;
    private long balance;
    private int multiplication;
    private String name;
    private String fightData;

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

    public long getBalance() {
        return balance;
    }

    public void setBalance(long balance) {
        this.balance = balance;
    }

    public String getOrganismName() {
        return name;
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

    public int attack(Organism defender) {
        synchronized (this) {
            synchronized (defender) {
                fightData = "";
                int result = NOT_FIGHTING_YET;

                if(isFightPossible(defender)) {
                    addFightersToFightAnnouncement(defender);

                    if (isDraw(defender)) {
                        balance = 0;
                        defender.balance = 0;
                        result = DRAW;
                    } else if (isDefenderWin(defender)) {
                        defender.balance = defender.balance - (balance / 2);
                        balance = 0;
                        result = DEFENDER_WIN;
                    } else {
                        balance = balance - (defender.balance / 2);
                        defender.balance = 0;
                        result = ATTACKER_WIN;
                    }
                    addResultToFightAnnouncement(defender, result);
                    LoggerUtil.logData(fightData);
                }

                return result;
            }

        }

    }

    private void increaseConstantly() {
        int propertyToIncrease = Randomizer.getRandomNumber(NUMBER_OF_PROPERTIES);

        if (propertyToIncrease == STRENGTH) {
            strength *= mutation;
        } else if (propertyToIncrease == INTELLIGENCE) {
            intelligence *= mutation;
        } else if (propertyToIncrease == TECHNOLOGICAL_MEANS) {
            technologicalMeans *= mutation;
        }

    }

    private void increaseOptionally() {
        int option = Randomizer.getRandomNumber(NUMBER_OF_OPTIONS);

        if (option == INCREASE)
            increaseProperties();
    }

    private void increaseBalance() {
        balance *= multiplication;
    }

    private void addFightersToFightAnnouncement(Organism defender) {
        fightData += "\nBattle Attacker -> " + toString() + "\n" +
                "Battle Defender -> " + defender.toString() + "\n" +
                name + " attack " + defender.name + " -> result = ";
    }

    private void addResultToFightAnnouncement(Organism defender, int result) {
        if(result == DRAW)
            addDraw();
        else if(result == DEFENDER_WIN)
            addDefenderWin(defender);
        else if(result == ATTACKER_WIN)
            addAttackerWin();
    }

    private void addDraw() {
        fightData += "Draw\n";
    }

    private void addDefenderWin(Organism defender) {
        fightData += defender.name + " wins\n";
    }

    private void addAttackerWin() {
        fightData += name + " wins\n";
    }

    private boolean isFightPossible(Organism defender) {
        return getSumOfProperties() - defender.getSumOfProperties() > DIFFERENCE_BETWEEN_PROPERTIES &&
                balance > 0 && defender.balance > 0;
    }

    private boolean isDraw(Organism defender) {
        return balance == (defender.balance / 2);
    }

    private boolean isDefenderWin(Organism defender) {
        return balance < (defender.balance / 2);
    }
}
