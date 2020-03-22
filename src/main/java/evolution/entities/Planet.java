package evolution.entities;

import evolution.entities.organisms.Organism;
import evolution.utilities.ExecutorServiceUtil;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;
import java.util.concurrent.ExecutorService;

public class Planet implements Runnable {
    private static Planet instance = null;
    private static Logger logger = LogManager.getLogger(Planet.class);
    private List<Continent> continents;
    private ExecutorService executor;

    private Planet() {
        notifyStart();
        this.executor = ExecutorServiceUtil.getExecutor();
    }

    public static Planet getInstance() {
        if(instance == null) {
            instance = new Planet();
        }

        return instance;
    }

    public List<Continent> getContinents() {
        return continents;
    }

    public void setContinents(List<Continent> continents) {
        this.continents = continents;
    }

    public List<Organism> getOrganismsInContinent(Continent continent) {
        return continent.getOrganisms();
    }

    public void addContinents(List<Continent> continentsList) {
        for(Continent continent : continentsList) {
            continents.add(continent);
        }
    }

    public void addContinent(Continent continent) {
        continents.add(continent);
    }

    public void run() {
        startLifeCycle();
    }

    private void notifyStart() {
        String startMessage = "Planet Started!";
        logger.info(startMessage);
    }

    private void startLifeCycle() {
        for(Continent continent: continents) {
            executor.execute(continent);
        }

        logger.info("______________");
    }
}
