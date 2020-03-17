package Places;

import Organisms.Organism;
import Utilities.ExecutorServiceUtil;
import Utilities.LoggerUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.TimerTask;
import java.util.concurrent.ExecutorService;

public class Planet extends TimerTask {
    private static Planet instance = null;
    private List<Continent> continents;
    private ExecutorService executor;
    private boolean isFirstCycle;

    private Planet(List<Continent> continents) {
        notifyStart();
        this.continents = continents;
        this.executor = ExecutorServiceUtil.getExecutor();
        this.isFirstCycle = true;
    }

    public static Planet getInstance(List<Continent> continents) {

        if(instance == null) {
            instance = new Planet(continents);
        }
        return instance;
    }

    private List<Organism> getOrganismsInContinent(Continent continent) {
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
        String startMessage = "Planet Started!" + "\n______________";
        LoggerUtil.logData(startMessage);
    }

    private synchronized void startLifeCycle() {

        for(int i = 0; i < continents.size(); i++) {
            executor.execute(continents.get(i));
        }

        if(isFirstCycle)
            isFirstCycle = false;
        else
            LoggerUtil.logData("______________");
    }
}
