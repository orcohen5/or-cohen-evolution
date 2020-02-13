package Events;

import Organisms.*;
import Places.Continent;
import Places.Planet;
import org.apache.log4j.BasicConfigurator;

public class Initializer {
    public static void initializeLog() {
        BasicConfigurator.configure();
    }

    public static void initializeEvolution() {
        Homosepian homosepian = new Homosepian("Nadav");
        Neanderthal neanderthal = new Neanderthal("Sapir");
        Homoerectus homoerectus = new Homoerectus("Tal");
        Homoebolis homoebolis = new Homoebolis("Yehuda");
        Homofloresiensis homofloresiensis = new Homofloresiensis("Or");
        Continent asia = new Continent("Asia");
        Continent africa = new Continent("Africa");
        asia.addOrganismToContinent(homosepian);
        asia.addOrganismToContinent(neanderthal);
        africa.addOrganismToContinent(homoerectus);
        africa.addOrganismToContinent(homoebolis);
        asia.addOrganismToContinent(homofloresiensis);
        Planet planet = Planet.getInstance();
        planet.addContinentToPlanet(asia);
        planet.addContinentToPlanet(africa);
        planet.runLifeCycleEverySecond();
    }
}
