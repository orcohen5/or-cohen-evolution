package Places;

import Organisms.Organism;

import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Continent extends Thread {
    private final int THREADS_NUMBER = 3;
    private String name;
    private ArrayList<Organism> organismsInContinent;
    private ExecutorService executor;

    public Continent(String name) {
        this.name = name;
        organismsInContinent = new ArrayList<Organism>();
    }

    public ArrayList<Organism> getOrganismsInContinent() {
        return organismsInContinent;
    }

    public void setOrganismsInContinent(ArrayList<Organism> organismsInContinent) {
        this.organismsInContinent = organismsInContinent;
    }

    public void addOrganismToContinent(Organism organism) {
        organismsInContinent.add(organism);
    }

    public void runCycle() {
        this.executor = Executors.newFixedThreadPool(THREADS_NUMBER);

        for(int i = 0; i < this.organismsInContinent.size(); i++) {
            executor.execute(this.organismsInContinent.get(i));
        }
        this.executor.shutdown();
    }

    @Override
    public void run() {
        runCycle();
        System.out.println(toString());
    }

    @Override
    public String toString() {
        String continentData = "";

        for(Organism organism : organismsInContinent) {
            continentData += name + " -> " + organism.toString() + "\n";
        }
        return continentData;
    }
}
