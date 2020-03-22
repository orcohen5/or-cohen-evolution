package org.iaf.evolution.entities;

import org.iaf.evolution.entities.organisms.Organism;
import org.iaf.evolution.utilities.ExecutorServiceUtil;
import org.iaf.evolution.utilities.LoggerUtil;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.*;

public class Continent extends Thread {
    private final int DRAW = 0;
    private final int DEFENDER_WIN = 1;
    private final int ATTACKER_WIN = 2;
    private String name;
    private List<Organism> organisms;
    private ExecutorService executor;

    public Continent(String name, List<Organism> organisms) {
        this.name = name;
        this.organisms = organisms;
        this.executor = ExecutorServiceUtil.getExecutor();
    }

    public String getContinentName() {
        return name;
    }

    public List<Organism> getOrganisms() {
        return organisms;
    }

    public void addOrganisms(List<Organism> organismsList) {

        for(Organism organism : organismsList) {
            organisms.add(organism);
        }

    }

    public void addOrganism(Organism organism) {
        organisms.add(organism);
    }

    public void removeOrganisms(List<Organism> organismsList) {
        Iterator<Organism> iterator = organismsList.iterator();

        while(iterator.hasNext()) {
            Organism defender = iterator.next();
            organisms.remove(defender);
        }

    }

    public void removeOrganism(Organism organism) {
        organisms.remove(organism);
    }

    @Override
    public void run() {
        startLifeCycle();
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

        return builder.toString();
    }

    private void startLifeCycle() {
        Future<?> task;

        for (Organism organism: organisms) {
            task = executor.submit(organism);

            while (task.isDone() == false) {
                try {
                    Thread.sleep(10);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

        }

        LoggerUtil.logData(getContinentData());
        startOrganismsFights();
    }

    private void startOrganismsFights() {
        List<Organism> organismsToRemove = new ArrayList();
        int result;

        for(Organism attacker : organisms) {

            for(Organism defender : organisms) {
                result = attacker.attack(defender);

                if(result == DRAW) {
                    organismsToRemove.add(attacker);
                    organismsToRemove.add(defender);
                } else if(result == DEFENDER_WIN) {
                    organismsToRemove.add(attacker);
                } else if(result == ATTACKER_WIN) {
                    organismsToRemove.add(defender);
                }

            }

        }

        removeOrganisms(organismsToRemove);
    }
}
