package Events;

import Organisms.*;
import Places.Continent;
import Places.Planet;

import java.util.ArrayList;
import java.util.Timer;

public class Initializer {
    private Planet planet;
    private Continent asia;
    private Continent africa;
    private ArrayList<Organism> asianOrganismsList;
    private ArrayList<Organism> africanOrganismsList;
    private ArrayList<Continent> continentsList;
    private Timer timer;

    public Initializer() {
        this.asianOrganismsList = new ArrayList();
        this.africanOrganismsList = new ArrayList();
        this.continentsList = new ArrayList();
    }

    public void initializeEvolution() {
        planet = Planet.getInstance();
        asia = new Continent("Asia");
        africa = new Continent("Africa");
        asianOrganismsList.add(new Homosepian("Nadav"));
        asianOrganismsList.add(new Neanderthal("Sapir"));
        africanOrganismsList.add(new Homoerectus("Tal"));
        africanOrganismsList.add(new Homoebolis("Yehuda"));
        asianOrganismsList.add(new Homofloresiensis("Or"));
        asia.add(asianOrganismsList);
        africa.add(africanOrganismsList);
        continentsList.add(asia);
        continentsList.add(africa);
        planet.add(continentsList);
    }

    public void startLifeCycle() {
        timer = new Timer();
        timer.schedule(planet, 0, 1000);
    }
}
