package evolution.entities;

import evolution.entities.organisms.Organism;
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
    private final int DIFFERENCE_BETWEEN_PROPERTIES = 5;
    private final int NUMBER_OF_ARTWORKS_FOR_GOLDEN_AGE = 5;
    private String continentName;
    private List<Organism> organismsInContinent;
    private ExecutorService executor;
    private int numberOfArtworks;

    public Continent(String continentName) {
        this.continentName = continentName;
        this.organismsInContinent = new ArrayList();
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

    public void addOrganismsToContinent(List<Organism> organismsList) {
        for(Organism organism : organismsList) {
            organismsInContinent.add(organism);
        }
    }

    public void addOrganismToContinent(Organism organism) {
        organismsInContinent.add(organism);
    }

    public void removeOrganismsFromContinent(List<Organism> organismsList) {
        Iterator<Organism> iterator = organismsList.iterator();

        while(iterator.hasNext()) {
            Organism defender = iterator.next();
            organismsInContinent.remove(defender);
        }
    }

    public void removeOrganismFromContinent(Organism organism) {
        organismsInContinent.remove(organism);
    }

    public void run() {
        startLifeCycle();
    }

    @Override
    public String toString() {
        return getContinentData();
    }

    private String getContinentData() {
        StringBuilder builder = new StringBuilder();

        for(Organism organism : organismsInContinent) {
            builder.append(continentName + " -> " + organism.toString() + System.getProperty("line.separator"));
        }

        return builder.toString();
    }

    private void startLifeCycle() {
        Future<?> organismTaskResult;

        for(Organism organism : organismsInContinent) {
            synchronized (organism) {
                organismTaskResult = executor.submit(organism);

                if(!organismTaskResult.isDone()) {
                    try {
                        Thread.sleep(30);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }

                numberOfArtworks += organism.contributeArtworkByOption();

                if(isGoldenAgePossible()) {
                    logger.info(continentName + " -> Golden age has happened!\n");
                    startGoldenAge();
                }
            }
        }

        logger.info(getContinentData());
        startOrganismsFights();
    }

    private boolean isGoldenAgePossible() {
        if(numberOfArtworks > NUMBER_OF_ARTWORKS_FOR_GOLDEN_AGE) {
            numberOfArtworks -= NUMBER_OF_ARTWORKS_FOR_GOLDEN_AGE;

            return true;
        } else if(numberOfArtworks == NUMBER_OF_ARTWORKS_FOR_GOLDEN_AGE) {
            numberOfArtworks = 0;
            
            return true;
        }

        return false;
    }

    private void startGoldenAge() {
        for(Organism organism : organismsInContinent)
            organism.setBalance((long) (organism.getBalance() * 1.5));
    }

    private void startOrganismsFights() {
        List<Organism> organismsToRemove = new ArrayList();
        int fightResult;

        for(Organism attacker : organismsInContinent) {
            for(Organism defender : organismsInContinent) {

                if(isFightPossible(attacker, defender)) {
                    fightResult = attacker.attack(defender);

                    if(fightResult == DRAW) {
                        organismsToRemove.add(attacker);
                        organismsToRemove.add(defender);
                    } else if(fightResult == DEFENDER_WIN) {
                        organismsToRemove.add(attacker);
                    } else if(fightResult == ATTACKER_WIN) {
                        organismsToRemove.add(defender);
                    }
                }
            }
        }

        removeOrganismsFromContinent(organismsToRemove);
    }

    private boolean isFightPossible(Organism attacker, Organism defender) {
        return (attacker.getSumOfProperties() - defender.getSumOfProperties() > DIFFERENCE_BETWEEN_PROPERTIES) &&
                (attacker.getBalance() > 0) && (defender.getBalance() > 0);
    }
}
