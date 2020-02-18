package Places;

import Utilities.ExecutorServiceUtil;
import Utilities.LoggerUtil;

import java.util.ArrayList;
import java.util.TimerTask;
import java.util.concurrent.ExecutorService;

public class Planet extends TimerTask {
    private static Planet instance = null;
    private ArrayList<Continent> continents;
    private ExecutorService executor;

    private Planet() {
        notifyStart();
        this.continents = new ArrayList();
        this.executor = ExecutorServiceUtil.getExecutor();
    }

    public static Planet getInstance() {

        if(instance == null) {
            instance = new Planet();
        }
        return instance;
    }

    public void add(ArrayList<Continent> continentsList) {

        for(Continent continent : continentsList) {
            continents.add(continent);
        }
    }

    public void add(Continent continent) {
        continents.add(continent);
    }

    public void run() {
        lifeCycle();
    }

    private void notifyStart() {
        String startMessage = "Planet Started!";
        LoggerUtil.logData(startMessage);
    }

    private void lifeCycle() {
        LoggerUtil.logData("______________");

        for(int i = 0; i < continents.size(); i++) {
            executor.execute(continents.get(i));
        }
    }
}
