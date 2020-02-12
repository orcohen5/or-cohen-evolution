package Places;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Planet extends TimerTask {
    private final int THREADS_NUMBER = 3;
    private static Planet instance = null;
    private ArrayList<Continent> continentsInPlanet;
    private ExecutorService executor;

    private Planet() {
        System.out.println("Planet Started!");
        this.continentsInPlanet = new ArrayList<Continent>();
        this.executor = Executors.newFixedThreadPool(THREADS_NUMBER);
    }

    public static Planet getInstance() {

        if(instance == null) {
            instance = new Planet();
        }
        return instance;
    }

    public ArrayList<Continent> getContinentsInPlanet() {
        return continentsInPlanet;
    }

    public void setContinentsInPlanet(ArrayList<Continent> continentsInPlanet) {
        this.continentsInPlanet = continentsInPlanet;
    }

    public void addContinentToPlanet(Continent continent) {
        this.continentsInPlanet.add(continent);
    }

    public void runCycle() {
        printDivider();

        for(int i = 0; i < this.continentsInPlanet.size(); i++) {
            this.executor.execute(this.continentsInPlanet.get(i));
        }
    }

    public void executeLifeCycleEverySecond() {
        Timer timer = new Timer();
        timer.schedule(this, 0, 1000);
    }

    public void printDivider() {
        System.out.println("______________");
    }


    public void run() {
        runCycle();
    }
}
