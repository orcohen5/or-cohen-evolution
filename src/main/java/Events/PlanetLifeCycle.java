package Events;

import Places.Continent;
import Places.Planet;

import java.util.ArrayList;
import java.util.TimerTask;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class PlanetLifeCycle extends TimerTask implements Runnable {
    private final int THREADS_NUMBER = 3;
    private Planet planet;
    private ArrayList<Continent> continentsList;
    private ArrayList<ContinentLifeCycle> continentsLifeCyclesList;
    private ExecutorService executor;
    private ContinentLifeCycle currentContinentCycle;

    public PlanetLifeCycle(Planet planet) {
        this.planet = planet;
        this.continentsList = this.planet.getContinentsInPlanet();
        this.continentsLifeCyclesList = new ArrayList<ContinentLifeCycle>();
        this.executor = Executors.newFixedThreadPool(THREADS_NUMBER);
    }

    public void runCycle() {
        printDivider();

        for(int i=0;i<this.continentsList.size();i++) {
            createAndRunContinentLifeCycle(i);
            addContinentCycleToList();
            updateContinentInList(i);
        }
        updateContinentsInPlanet();
    }

    public void createAndRunContinentLifeCycle(int index) {
        createContinentLifeCycle(index);
        runContinentLifeCycle();
    }

    public void addContinentCycleToList() {
        this.continentsLifeCyclesList.add(currentContinentCycle);
    }

    public void updateContinentInList(int index) {
        this.continentsList.set(index,currentContinentCycle.getContinent());
    }

    public void updateContinentsInPlanet() {
        this.planet.setContinentsInPlanet(this.continentsList);
    }

    public void createContinentLifeCycle(int index) {
        Continent continentInCycle = this.continentsList.get(index);
        this.currentContinentCycle = new ContinentLifeCycle(continentInCycle);
    }

    public void runContinentLifeCycle() {
        this.executor.execute(this.currentContinentCycle);
    }

    public void printDivider() {
        System.out.println("______________");
    }

    public void run() {
        runCycle();
    }
}
