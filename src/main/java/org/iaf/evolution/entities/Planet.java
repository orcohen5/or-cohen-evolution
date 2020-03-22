package org.iaf.evolution.entities;

import org.iaf.evolution.entities.organisms.Organism;
import org.iaf.evolution.utilities.ExecutorServiceUtil;
import org.iaf.evolution.utilities.LoggerUtil;

import java.util.List;
import java.util.TimerTask;
import java.util.concurrent.ExecutorService;

public class Planet extends TimerTask {
    private static Planet instance = null;
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
        LoggerUtil.logData(startMessage);
    }

    private void startLifeCycle() {

        for(Continent continent: continents) {
            executor.execute(continent);
        }

        LoggerUtil.logData("______________");
    }
}
