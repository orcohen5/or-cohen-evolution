package evolution.entities.organisms;

import evolution.utilities.Randomizer;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import static java.lang.Thread.currentThread;

public abstract class Organism implements Runnable {
    private static Logger logger = LogManager.getLogger(Organism.class);
    private final int STRENGTH = 1;
    private final int INTELLIGENCE = 2;
    private final int TECHNOLOGICAL_MEANS = 3;
    private final int NUMBER_OF_PROPERTIES = 3;
    private final int INCREASE = 1;
    private final int NUMBER_OF_OPTIONS = 5;
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
                StringBuilder builder = new StringBuilder();
                int result = NOT_FIGHTING_YET;
                addFightersToFightAnnouncement(defender, builder);

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
                addResultToFightAnnouncement(defender, result, builder);
                logger.info(builder.toString());

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

    private void addFightersToFightAnnouncement(Organism defender, StringBuilder builder) {
        builder.append("\nBattle Attacker -> " + toString() + "\n" +
                "Battle Defender -> " + defender.toString() + "\n" +
                name + " attack " + defender.name + " -> result = ");
    }

    private void addResultToFightAnnouncement(Organism defender, int result, StringBuilder builder) {
        if(result == DRAW)
            addDraw(builder);
        else if(result == DEFENDER_WIN)
            addDefenderWin(defender, builder);
        else if(result == ATTACKER_WIN)
            addAttackerWin(builder);
    }

    private void addDraw(StringBuilder builder) {
        builder.append("Draw\n");
    }

    private void addDefenderWin(Organism defender, StringBuilder builder) {
        builder.append(defender.name + " wins\n");
    }

    private void addAttackerWin(StringBuilder builder) {
        builder.append(name + " wins\n");
    }

    private boolean isDraw(Organism defender) {
        return balance == (defender.balance / 2);
    }

    private boolean isDefenderWin(Organism defender) {
        return balance < (defender.balance / 2);
    }
}
