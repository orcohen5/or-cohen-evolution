package Places;

import Organisms.Organism;
import org.apache.log4j.Logger;
import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Continent extends Thread {
    private final Logger log = Logger.getLogger(Continent.class);
    private final int THREADS_NUMBER = 3;
    private String name;
    private ArrayList<Organism> organismsInContinent;
    private ExecutorService executor;

    public Continent(String name) {
        this.name = name;
        this.organismsInContinent = new ArrayList<Organism>();
    }

    public void addOrganismToContinent(Organism organism) {
        this.organismsInContinent.add(organism);
    }

    @Override
    public void run() {
        runCycleInContinent();

    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append(System.getProperty("line.separator"));

        for(Organism organism : this.organismsInContinent) {
            builder.append(this.name);
            builder.append(" -> ");
            builder.append(organism.toString());
            builder.append(System.getProperty("line.separator"));
        }
        String continentData = builder.toString();
        return continentData;
    }

    private void runCycleInContinent() {
        runOrganismsLifeCycle();
        log.info(toString());
    }

    private void runOrganismsLifeCycle() {
        this.executor = Executors.newFixedThreadPool(THREADS_NUMBER);
        runLifeCycleOfEachOrganism();
        this.executor.shutdown();
    }

    private void runLifeCycleOfEachOrganism() {

        for(int i = 0; i < this.organismsInContinent.size(); i++) {
            this.executor.execute(this.organismsInContinent.get(i));
        }
    }
}
