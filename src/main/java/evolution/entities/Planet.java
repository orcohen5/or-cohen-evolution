package evolution.entities;

import evolution.entities.organisms.Organism;
import evolution.utilities.ExecutorServiceUtil;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;
import java.util.concurrent.ExecutorService;

public class Planet implements Runnable {
    private static Planet instance;
    private static Logger logger = LogManager.getLogger(Planet.class);
    private List<Continent> continentsInPlanet;
    private ExecutorService planetExecutor;
    private boolean isFirstCycle;

    private Planet() {
        this.planetExecutor = ExecutorServiceUtil.getExecutor();
        this.isFirstCycle = true;
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

    public void run() {
        startLifeCycle();
    }

    private void startLifeCycle() {
        if(isFirstCycle) {
            notifyStart();
            isFirstCycle = false;
        }

        for(Continent continent: continentsInPlanet) {
            planetExecutor.execute(continent);
        }

        logger.info("______________");
    }

    private void notifyStart() {
        String startMessage = "Planet Started!";
        logger.info(startMessage);
    }
}
