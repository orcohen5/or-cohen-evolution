package Places;

import Events.PlanetLifeCycle;

import java.util.ArrayList;
import java.util.Timer;

public class Planet {

    private static Planet instance = null;
    private ArrayList<Continent> continentsInPlanet;

    private Planet() {
        System.out.println("Planet Started!");
        this.continentsInPlanet = new ArrayList<Continent>();
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

    public ArrayList<Continent> getContinentsInPlanet() {
        return continentsInPlanet;
    }

    public void setContinentsInPlanet(ArrayList<Continent> continentsInPlanet) {
        this.continentsInPlanet = continentsInPlanet;
    }

    public void runPlanetCycle() {
        Timer timer = new Timer();
        timer.schedule(new PlanetLifeCycle(this), 0, 1000);
    }
}
