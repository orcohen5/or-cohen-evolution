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
    private String name;
    private List<Organism> organisms;
    private ExecutorService executor;
    private int numberOfArtworks;

    public Continent(String name) {
        this.name = name;
        this.organisms = new ArrayList();
        this.executor = ExecutorServiceUtil.getExecutor();
        this.numberOfArtworks = 0;
    }

    public Continent(String name, List<Organism> organisms) {
        this(name);
        this.organisms = organisms;
    }

    public String getName() {
        return name;
    }

    public List<Organism> getOrganisms() {
        return organisms;
    }

    public void setOrganisms(List<Organism> organisms) {
        this.organisms = organisms;
    }

    public void addOrganisms(List<Organism> organismsList) {
        for(Organism organism : organismsList) {
            organisms.add(organism);
        }
    }

    public void addOrganism(Organism organism) {
        organisms.add(organism);
    }

    public void removeOrganismsFromContinent(List<Organism> organismsList) {
        Iterator<Organism> iterator = organismsList.iterator();

        while(iterator.hasNext()) {
            Organism defender = iterator.next();
            organisms.remove(defender);
        }
    }

    public void removeOrganismFromContinent(Organism organism) {
        organisms.remove(organism);
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

        for(Organism organism : organisms) {
            builder.append(name + " -> " + organism.toString() + System.getProperty("line.separator"));
        }

        return builder.toString();
    }

    private void startLifeCycle() {
        Future<?> organismTaskResult;

        for(Organism organism : organisms) {
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
                    logger.info(name + " -> Golden age has happened!\n");
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
        for(Organism organism : organisms)
            organism.setBalance((long) (organism.getBalance() * 1.5));
    }

    private void startOrganismsFights() {
        List<Organism> organismsToRemove = new ArrayList();
        int fightResult;

        for(Organism attacker : organisms) {
            for(Organism defender : organisms) {

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
