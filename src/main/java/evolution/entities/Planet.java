package evolution.entities;

import evolution.entities.organisms.Organism;
import evolution.utilities.ExecutorServiceUtil;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;

public class Planet implements Runnable {
    private static Planet instance;
    private static Logger logger = LogManager.getLogger(Planet.class);
    private final int DRAW = 0;
    private final int DEFENDER_WIN = 1;
    private final int ATTACKER_WIN = 2;
    private final int DIFFERENCE_BETWEEN_PROPERTIES = 10;
    private final int DIFFERENCE_BETWEEN_TECHNOLOGICAL_MEANS = 40;
    private List<Continent> continentsInPlanet;
    private ExecutorService planetExecutor;

    private Planet() {
        this.planetExecutor = ExecutorServiceUtil.getExecutor();
    }

    public static Planet getInstance() {
        if(instance == null) {
            instance = new Planet();
        }

        return instance;
    }

    public List<Continent> getContinentsInPlanet() {
        return continentsInPlanet;
    }

    public void setContinentsInPlanet(List<Continent> continentsInPlanet) {
        this.continentsInPlanet = continentsInPlanet;
    }

    public List<Organism> getOrganismsInContinent(Continent continent) {
        return continent.getOrganismsInContinent();
    }

    public void addContinentsToPlanet(List<Continent> continentsList) {
        for(Continent continent : continentsList) {
            continentsInPlanet.add(continent);
        }
    }

    public void addContinentToPlanet(Continent continent) {
        continentsInPlanet.add(continent);
    }

    public void removeContinentsFromPlanet(List<Continent> continents) {
        Iterator<Continent> iterator = continents.iterator();

        while(iterator.hasNext()) {
            Continent loser = iterator.next();
            this.continentsInPlanet.remove(loser);
        }
    }

    public void removeContinentFromPlanet(Continent continent) {
        List<Continent> continents = new ArrayList();
        removeContinentsFromPlanet(continents);
    }

    public void run() {
        startLifeCycle();
    }

    private void startLifeCycle() {
        Future<?> continentTaskResult;

        for(Continent continent: continentsInPlanet) {
            synchronized (continent) {
                continentTaskResult = planetExecutor.submit(continent);

                if(!continentTaskResult.isDone()) {
                    try {
                        Thread.sleep(135);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }

            if(continent.getOrganismsInContinent().size() == 0) {
                removeContinentFromPlanet(continent);
            }
        }

        startFightBetweenContinentsWinners();
        logger.info("______________");
    }

    private void startFightBetweenContinentsWinners() {
        List<Continent> losingContinents = new ArrayList();
        int fightResult;

        for(Continent attackingContinent : continentsInPlanet) {
            for(Continent defendingContinent : continentsInPlanet) {

                if(isFightPossible(attackingContinent, defendingContinent)) {
                    removeOrganismsDiedInTransit(attackingContinent);
                    fightResult = attackingContinent.attack(defendingContinent);

                    if(fightResult == DRAW) {
                        losingContinents.add(defendingContinent);
                        losingContinents.add(attackingContinent);
                    } else if(fightResult == DEFENDER_WIN) {
                        losingContinents.add(attackingContinent);
                    } else {
                        losingContinents.add(defendingContinent);
                    }
                }
            }
        }

        removeContinentsFromPlanet(losingContinents);

        if(continentsInPlanet.size() == 1 && continentsInPlanet.get(0).getOrganismsInContinent().size() == 1) {
            stopPlanet();
        }
    }

    private boolean isFightPossible(Continent attackingContinent, Continent defendingContinent) {
        if(attackingContinent.getOrganismsInContinent().size() == 1  && defendingContinent.getOrganismsInContinent().size() == 1)
            return attackingContinent.getFirstOrganismInContinent().getSumOfProperties() - defendingContinent.getFirstOrganismInContinent().getSumOfProperties() >= DIFFERENCE_BETWEEN_PROPERTIES &&
                    attackingContinent.getFirstOrganismInContinent().getTechnologicalMeans() - defendingContinent.getFirstOrganismInContinent().getTechnologicalMeans() >= DIFFERENCE_BETWEEN_TECHNOLOGICAL_MEANS &&
                    attackingContinent.getFirstOrganismInContinent().getBalance() > 0 && defendingContinent.getFirstOrganismInContinent().getBalance() > 0;

        return false;
    }

    private void removeOrganismsDiedInTransit(Continent continent) {
        List<Organism> organismsInContinent = continent.getOrganismsInContinent();
        Organism organism = organismsInContinent.get(0);
        organism.setBalance((long) (organism.getBalance() * 0.9));
    }

    private void stopPlanet() {
        logger.info("Planet Stopped!");
        System.exit(0);
    }


}
