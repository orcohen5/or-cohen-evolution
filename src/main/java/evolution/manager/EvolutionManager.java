package evolution.manager;

import evolution.entities.organisms.*;
import evolution.entities.Continent;
import evolution.entities.Planet;
import evolution.utilities.ExecutorServiceUtil;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.*;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class EvolutionManager {
    private static Logger logger = LogManager.getLogger(EvolutionManager.class);
    private ScheduledExecutorService executorScheduler;
    private Planet planet;

    public EvolutionManager() {
        this.executorScheduler = ExecutorServiceUtil.getExecutorScheduler();
    }

    public void initializeEvolution() {
        List<Organism> asianOrganismsList = new ArrayList();
        List<Organism> africanOrganismsList = new ArrayList();
        List<Organism> europeanOrganismsList = new ArrayList();
        List<Organism> americanOrganismsList = new ArrayList();
        List<Continent> continentsList = new ArrayList();

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

        continentsList.add(new Continent("Asia", asianOrganismsList));
        continentsList.add(new Continent("Africa", africanOrganismsList));
        continentsList.add(new Continent("Europe", europeanOrganismsList));
        continentsList.add(new Continent("America", americanOrganismsList));

        planet = Planet.getInstance();
        planet.setContinentsInPlanet(continentsList);
    }

    public void startEvolution() {
        notifyStart();
        executorScheduler.scheduleAtFixedRate(planet, 0, 1, TimeUnit.SECONDS);
    }

    private void notifyStart() {
        String startMessage = "Planet Started!";
        logger.info(startMessage);
    }
}
