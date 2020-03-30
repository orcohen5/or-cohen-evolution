package evolution.entities.organisms;

import evolution.exceptions.CivilWarException;
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
    private final int CIVIL_WAR = 1;
    private final int NUMBER_OF_OPTIONS_TO_INCREASE = 5;
    private final int NUMBER_OF_OPTIONS_TO_CIVIL_WAR = 10;
    private final int NUMBER_OF_OPTIONS_TO_CONTRIBUTION = 3;
    private final int CONTRIBUTE = 1;
    private final int ONE_NEW_ARTWORK = 1;
    private final int NO_NEW_ARTWORK = 0;
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
        startLifeCycle();
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
                StringBuilder fightData = new StringBuilder();
                int fightResult;
                addFightersToFightAnnouncement(defender, fightData);

                if (isDraw(defender)) {
                    balance = 0;
                    defender.balance = 0;
                    fightResult = DRAW;
                } else if (isDefenderWin(defender)) {
                    defender.balance = defender.balance - (balance / 2);
                    balance = 0;
                    fightResult = DEFENDER_WIN;
                } else {
                    balance = balance - (defender.balance / 2);
                    defender.balance = 0;
                    fightResult = ATTACKER_WIN;
                }

                if(fightResult == DRAW)
                    addDrawToFightAnnouncement(fightData);
                else if(fightResult == DEFENDER_WIN)
                    addDefenderWinToFightAnnouncement(defender, fightData);
                else if(fightResult == ATTACKER_WIN)
                    addAttackerWinToFightAnnouncement(fightData);
                logger.info(fightData.toString());

                return fightResult;
            }
        }
    }

    private void startLifeCycle() {
        increaseBalance();

        if(isCivilWarPossible()) {
            startCivilWar();
        } else {
            increaseConstantly();
            increaseOptionally();
        }
    }

    private void increaseBalance() {
        balance *= multiplication;
    }

    private boolean isCivilWarPossible() {
        int option = Randomizer.getRandomNumber(NUMBER_OF_OPTIONS_TO_CIVIL_WAR);

        if(option == CIVIL_WAR)
            return true;
        return false;
    }

    private void startCivilWar() {
        long oldBalance = balance;
        balance = balance / 2;

        try {
            throw new CivilWarException("Civil war has happened in " + getOrganismName() +
                    " - now the balance has decreased from " + oldBalance + " to " + balance + "\n");
        } catch (CivilWarException e) {
            logger.warn(e.getMessage());
        }
    }

    public int contributeArtworkByOption() {
        int option = Randomizer.getRandomNumber(NUMBER_OF_OPTIONS_TO_CONTRIBUTION);

        if(option == CONTRIBUTE) {
            logger.info(getOrganismName() + " has contributed to the golden age\n");
            return ONE_NEW_ARTWORK;
        } else {
            return NO_NEW_ARTWORK;
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
        int option = Randomizer.getRandomNumber(NUMBER_OF_OPTIONS_TO_INCREASE);

        if (option == INCREASE)
            increaseProperties();
    }

    private void addFightersToFightAnnouncement(Organism defender, StringBuilder fightData) {
        fightData.append("\nBattle Attacker -> " + toString() + "\n" +
                "Battle Defender -> " + defender.toString() + "\n" +
                name + " attack " + defender.name + " -> result = ");
    }

    private void addDrawToFightAnnouncement(StringBuilder fightData) {
        fightData.append("Draw\n");
    }

    private void addDefenderWinToFightAnnouncement(Organism defender, StringBuilder fightData) {
        fightData.append(defender.name + " wins\n");
    }

    private void addAttackerWinToFightAnnouncement(StringBuilder fightData) {
        fightData.append(name + " wins\n");
    }

    private boolean isDraw(Organism defender) {
        return balance == (defender.balance / 2);
    }

    private boolean isDefenderWin(Organism defender) {
        return balance < (defender.balance / 2);
    }
}
