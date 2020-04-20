package evolution.entities;

import evolution.entities.organisms.Organism;
import evolution.utilities.DynamicList;
import evolution.utilities.ExecutorServiceUtil;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.*;

public class Continent implements Runnable {
    private static Logger logger = LogManager.getLogger(Continent.class);
    private final int DRAW = 0;
    private final int DEFENDER_WIN = 1;
    private final int ATTACKER_WIN = 2;
    private final int CONTINENTAL = 0;
    private final int INTERCONTINENTAL = 1;
    private final int DIFFERENCE_BETWEEN_PROPERTIES = 5;
    private final int ARTWORKS_FOR_GOLDEN_AGE = 5;
    private final double GOLDEN_AGE_MULTIPLIER = 1.5;
    private String continentName;
    private List<Organism> organismsInContinent;
    private ExecutorService executor;
    private int numberOfArtworks;

    public Continent(String continentName) {
        this.continentName = continentName;
        this.organismsInContinent = new DynamicList<Organism>();
        this.executor = ExecutorServiceUtil.getExecutor();
        this.numberOfArtworks = 0;
    }

    public Continent(String continentName, List<Organism> organismsInContinent) {
        this(continentName);
        this.organismsInContinent = organismsInContinent;
    }

    public String getContinentName() {
        return continentName;
    }

    public List<Organism> getOrganismsInContinent() {
        return organismsInContinent;
    }

    public void setOrganismsInContinent(List<Organism> organismsInContinent) {
        this.organismsInContinent = organismsInContinent;
    }

    public Organism getFirstOrganismInContinent() {
        List<Organism> organismsInContinent = getOrganismsInContinent();
        Organism firstOrganism = organismsInContinent.get(0);

        return firstOrganism;
    }

    public void addOrganismsToContinent(List<Organism> organismsList) {
        for(Organism organism : organismsList) {
            organismsInContinent.add(organism);
        }
    }

    public void addOrganismToContinent(Organism organism) {
        organismsInContinent.add(organism);
    }

    public void removeOrganismsFromContinent(List<Organism> organisms) {
        Iterator<Organism> iterator = organisms.iterator();

        while(iterator.hasNext()) {
            Organism loser = iterator.next();
            this.organismsInContinent.remove(loser);
        }
    }

    public void removeOrganismFromContinent(Organism organism) {
        organismsInContinent.remove(organism);
    }

    public void run() {
        startLifeCycle();
    }

    public int attack(Continent defendingContinent) {
        int fightResult;

        synchronized (this) {
            synchronized (defendingContinent) {
                StringBuilder fightData = new StringBuilder();
                Organism attacker = getFirstOrganismInContinent();
                Organism defender = defendingContinent.getFirstOrganismInContinent();
                addFightersToFightAnnouncement(defendingContinent, fightData);
                fightResult = attacker.attack(defender, INTERCONTINENTAL);

                if(fightResult == DRAW) {
                    addDrawToFightAnnouncement(fightData);
                } else if(fightResult == DEFENDER_WIN) {
                    addDefenderWinToFightAnnouncement(defendingContinent, fightData);
                } else if(fightResult == ATTACKER_WIN) {
                    addAttackerWinToFightAnnouncement(fightData);
                }

                logger.info(fightData.toString());

                return fightResult;
            }
        }
    }

    @Override
    public String toString() {
        return getContinentData();
    }

    private String getContinentData() {
        StringBuilder continentBuilder = new StringBuilder();

        for(Organism organism : organismsInContinent) {
            continentBuilder.append(continentName + " -> " + organism.toString() + System.getProperty("line.separator"));
        }

        return continentBuilder.toString();
    }

    private void startLifeCycle() {
        Future<?> organismTaskResult;

        for(Organism organism : organismsInContinent) {
            synchronized (organism) {
                organismTaskResult = executor.submit(organism);

                if(!organismTaskResult.isDone()) {
                    try {
                        Thread.sleep(40);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }

                numberOfArtworks += organism.contributeArtworkOptionally();

                if(isGoldenAgePossible()) {
                    numberOfArtworks -= ARTWORKS_FOR_GOLDEN_AGE;
                    logger.info(continentName + " -> Golden age has happened!\n");
                    startGoldenAge();
                }
            }
        }
        ((DynamicList) organismsInContinent).sortByComparable();
        logger.info(getContinentData());
        startOrganismsFights();

    }

    private boolean isGoldenAgePossible() {
        if(numberOfArtworks >= ARTWORKS_FOR_GOLDEN_AGE) {
            return true;
        }
        return false;
    }

    private void startGoldenAge() {
        for(Organism organism : organismsInContinent)
            organism.setBalance((long) (organism.getBalance() * GOLDEN_AGE_MULTIPLIER));
    }

    private void startOrganismsFights() {
        List<Organism> losingOrganisms = new ArrayList();
        int fightResult;

        for(Organism attacker : organismsInContinent) {
            for(Organism defender : organismsInContinent) {

                if(isFightPossible(attacker, defender)) {
                    fightResult = attacker.attack(defender, CONTINENTAL);

                    if(fightResult == DRAW) {
                        losingOrganisms.add(attacker);
                        losingOrganisms.add(defender);
                    } else if(fightResult == DEFENDER_WIN) {
                        losingOrganisms.add(attacker);
                    } else if(fightResult == ATTACKER_WIN) {
                        losingOrganisms.add(defender);
                    }
                }
            }
        }

        removeOrganismsFromContinent(losingOrganisms);
    }

    private boolean isFightPossible(Organism attacker, Organism defender) {
        return (attacker.getSumOfProperties() - defender.getSumOfProperties() > DIFFERENCE_BETWEEN_PROPERTIES) &&
                (attacker.getBalance() > 0) && (defender.getBalance() > 0);
    }

    private void addFightersToFightAnnouncement(Continent defendingContinent, StringBuilder fightData) {
        fightData.append("\nContinent Battle Attacker -> " + getContinentData() +
                "Continent Battle Defender -> " + defendingContinent.getContinentData() +
                continentName + ":" + getFirstOrganismNameInContinent() + " attack " + defendingContinent.continentName + ":" + defendingContinent.getFirstOrganismNameInContinent() + " -> result = ");
    }

    private void addDrawToFightAnnouncement(StringBuilder fightData) {
        fightData.append("Draw\n");
    }

    private void addDefenderWinToFightAnnouncement(Continent defendingContinent, StringBuilder fightData) {
        fightData.append(defendingContinent.continentName + ":" + defendingContinent.getFirstOrganismNameInContinent() + " wins\n" +
                "Winner -> " + defendingContinent.getContinentData());
    }

    private void addAttackerWinToFightAnnouncement(StringBuilder fightData) {
        fightData.append(continentName + ":" + getFirstOrganismNameInContinent() + " wins\n" +
                "Winner -> " + getContinentData());
    }

    private String getFirstOrganismNameInContinent() {
        return getFirstOrganismInContinent().getOrganismName();
    }
}
