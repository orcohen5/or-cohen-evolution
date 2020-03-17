package Events;

import Organisms.*;
import Places.Continent;
import Places.Planet;
import Utilities.ExecutorServiceUtil;

import java.util.*;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class EvolutionManager {
    private Planet planet;
    private Continent asia;
    private Continent africa;
    private Continent europe;
    private Continent america;
    private List<Organism> asianOrganismsList;
    private List<Organism> africanOrganismsList;
    private List<Organism> europeanOrganismsList;
    private List<Organism> americanOrganismsList;
    private List<Continent> continentsList;
    private ScheduledExecutorService startExecutor;

    public EvolutionManager() {
        this.asianOrganismsList = new ArrayList();
        this.africanOrganismsList = new ArrayList();
        this.europeanOrganismsList = new ArrayList();
        this.americanOrganismsList = new ArrayList();
        this.continentsList = new ArrayList();
        this.startExecutor = ExecutorServiceUtil.getStartExecutor();
    }

    public void initializeEvolution() {
        asianOrganismsList.add(new Homosepian("Nadav"));
        asianOrganismsList.add(new Homoebolis("Yehuda"));
        asianOrganismsList.add(new Homofloresiensis("Or"));
        africanOrganismsList.add(new Neanderthal("Demoza"));
        africanOrganismsList.add(new Homoerectus("Benny"));
        africanOrganismsList.add(new Homosepian("Zohar"));
        europeanOrganismsList.add(new Homoerectus("Tal"));
        europeanOrganismsList.add(new Neanderthal("Sapir"));
        europeanOrganismsList.add(new Homofloresiensis("Netanel"));
        americanOrganismsList.add(new Homosepian("Shalom"));
        americanOrganismsList.add(new Neanderthal("Ori"));
        americanOrganismsList.add(new Homoebolis("Efi"));
        asia = new Continent("Asia", asianOrganismsList);
        africa = new Continent("Africa", africanOrganismsList);
        europe = new Continent("Europe", europeanOrganismsList);
        america = new Continent("America", americanOrganismsList);
        continentsList.add(asia);
        continentsList.add(africa);
        continentsList.add(europe);
        continentsList.add(america);
        planet = Planet.getInstance(continentsList);
    }

    public void startEvolution() {
        startExecutor.scheduleAtFixedRate(planet, 0, 1, TimeUnit.SECONDS);
    }
}
