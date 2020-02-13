package Places;

import org.apache.log4j.Logger;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Planet extends TimerTask {
    private static Planet instance = null;
    private final Logger log = Logger.getLogger(Planet.class);
    private final int THREADS_NUMBER = 3;
    private ArrayList<Continent> continentsInPlanet;
    private ExecutorService executor;

    private Planet() {
        printStartMessage();
        this.continentsInPlanet = new ArrayList<Continent>();
        this.executor = Executors.newFixedThreadPool(THREADS_NUMBER);
    }

    public static Planet getInstance() {

        if(instance == null) {
            instance = new Planet();
        }
        return instance;
    }

    public void addContinentToPlanet(Continent continent) {
        this.continentsInPlanet.add(continent);
    }

    public void runLifeCycleEverySecond() {
        Timer timer = new Timer();
        timer.schedule(this, 0, 1000);
    }

    public void run() {
        runPlanetCycle();
    }

    private void printStartMessage() {
        StringBuilder builder = new StringBuilder();
        builder.append(System.getProperty("line.separator"));
        builder.append("Planet Started!");
        String startMessage = builder.toString();
        log.debug(startMessage);
    }

    private void runPlanetCycle() {
        printDivider();
        runLifeCycleInContinents();
    }

    private void printDivider() {
        StringBuilder builder = new StringBuilder();
        builder.append(System.getProperty("line.separator"));
        builder.append("______________");
        String divider = builder.toString();
        log.debug(divider);
    }

    private void runLifeCycleInContinents() {

        for(int i = 0; i < this.continentsInPlanet.size(); i++) {
            this.executor.execute(this.continentsInPlanet.get(i));
        }
    }
}
