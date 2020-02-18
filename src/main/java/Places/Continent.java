package Places;

import Organisms.Organism;
import Utilities.ExecutorServiceUtil;
import Utilities.LoggerUtil;

import java.util.ArrayList;
import java.util.concurrent.ExecutorService;

public class Continent extends Thread {
    private String name;
    private ArrayList<Organism> organisms;
    private ExecutorService executor;

    public Continent(String name) {
        this.name = name;
        this.organisms = new ArrayList();
        this.executor = ExecutorServiceUtil.getExecutor();
    }

    public void add(ArrayList<Organism> organismsList) {

        for(Organism organism : organismsList) {
            organisms.add(organism);
        }
    }

    public void add(Organism organism) {
        organisms.add(organism);
    }

    @Override
    public void run() {
        lifeCycle();
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
        String continentData = builder.toString();
        return continentData;
    }

    private void lifeCycle() {

        for(int i = 0; i < organisms.size(); i++) {
            executor.execute(organisms.get(i));
        }
        LoggerUtil.logData(getContinentData());
    }
}
