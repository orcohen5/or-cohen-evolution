package evolution.manager;

import evolution.entities.organisms.*;
import evolution.entities.Continent;
import evolution.entities.Planet;
import evolution.utilities.DynamicList;
import evolution.utilities.ExecutorServiceUtil;
import evolution.utilities.OrganismFactory;
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
        List<Organism> asianOrganismsList = new DynamicList();
        List<Organism> africanOrganismsList = new DynamicList();
        List<Organism> europeanOrganismsList = new DynamicList();
        List<Organism> americanOrganismsList = new DynamicList();
        List<Continent> continentsList = new ArrayList();
        OrganismFactory factory = new OrganismFactory();

        asianOrganismsList.add(factory.getOrganism("Nadav", "Homosepian"));
        asianOrganismsList.add(factory.getOrganism("Yehuda", "Homoebolis"));
        asianOrganismsList.add(factory.getOrganism("Or", "Homofloresiensis"));
        africanOrganismsList.add(factory.getOrganism("Demoza", "Neanderthal"));
        africanOrganismsList.add(factory.getOrganism("Benny", "Homoerectus"));
        africanOrganismsList.add(factory.getOrganism("Zohar", "Homosepian"));
        europeanOrganismsList.add(factory.getOrganism("Tal", "Homoerectus"));
        europeanOrganismsList.add(factory.getOrganism("Sapir", "Neanderthal"));
        europeanOrganismsList.add(factory.getOrganism("Netanel", "Homofloresiensis"));
        americanOrganismsList.add(factory.getOrganism("Shalom", "Homosepian"));
        americanOrganismsList.add(factory.getOrganism("Ori", "Neanderthal"));
        americanOrganismsList.add(factory.getOrganism("Efi", "Homoebolis"));

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
        String startMessage = "Planet Started!" + "\n______________";
        logger.info(startMessage);
    }
}
