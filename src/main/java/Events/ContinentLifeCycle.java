package Events;

import Organisms.Tribe;
import Places.Continent;

import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ContinentLifeCycle implements Runnable{
    private final int THREADS_NUMBER = 3;
    private Continent continent;
    private ArrayList<Tribe> tribesList;
    private ArrayList<TribeLifeCycle> tribesLifeCyclesList;
    private ExecutorService executor;
    private TribeLifeCycle currentTribeCycle;

    public ContinentLifeCycle(Continent continent) {
        this.continent = continent;
        this.tribesList = this.continent.getTribesInContinent();
        this.tribesLifeCyclesList = new ArrayList<TribeLifeCycle>();
    }

    public Continent getContinent() {
        return continent;
    }

    public void runCycle() {
        this.executor = Executors.newFixedThreadPool(THREADS_NUMBER);

        for(int i=0;i<continent.getTribesInContinent().size();i++) {
            createAndRunTribeLifeCycle(i);
            addTribeCycleToList();
            updateTribeInList(i);
        }
        this.executor.shutdown();
        updateTribesInContinent();
    }

    public void createAndRunTribeLifeCycle(int index) {
        createTribeLifeCycle(index);
        runTribeLifeCycle();
    }

    public void addTribeCycleToList() {
        this.tribesLifeCyclesList.add(this.currentTribeCycle);
    }

    public void updateTribeInList(int index) {
        this.tribesList.set(index,currentTribeCycle.getTribe());
    }

    public void updateTribesInContinent() {
        this.continent.setTribesInContinent(tribesList);
    }

    public void createTribeLifeCycle(int index) {
        Tribe tribeInCycle = this.tribesList.get(index);
        this.currentTribeCycle = new TribeLifeCycle(tribeInCycle);
    }

    public void runTribeLifeCycle() {
        executor.execute(currentTribeCycle);
    }

    public void run() {
        runCycle();
        System.out.println(this.continent.toString());
    }
}
