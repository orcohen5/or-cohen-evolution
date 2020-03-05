package Events;

import Organisms.*;
import Places.Continent;
import Places.Planet;

import java.util.*;

public class EvolutionManager {
    private Planet planet;
    private Continent asia;
    private Continent africa;
    private List<Organism> asianOrganismsList;
    private List<Organism> africanOrganismsList;
    private List<Continent> continentsList;
    private Timer timer;

    public EvolutionManager() {
        this.asianOrganismsList = new ArrayList();
        this.africanOrganismsList = new ArrayList();
        this.continentsList = new ArrayList();
    }

    public void initializeEvolution() {
        asianOrganismsList.add(new Homosepian("Nadav"));
        africanOrganismsList.add(new Neanderthal("Sapir"));
        africanOrganismsList.add(new Homoerectus("Tal"));
        asianOrganismsList.add(new Homoebolis("Yehuda"));
        africanOrganismsList.add(new Homosepian("Shalom"));
        asianOrganismsList.add(new Homofloresiensis("Or"));
        asia = new Continent("Asia", asianOrganismsList);
        africa = new Continent("Africa", africanOrganismsList);
        continentsList.add(asia);
        continentsList.add(africa);
        planet = Planet.getInstance(continentsList);
    }

    public void startEvolution() {
        timer = new Timer();
        timer.schedule(planet, 0, 1000);
    }
}
