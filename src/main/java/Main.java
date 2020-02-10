import Organisms.*;
import Places.Continent;
import Places.Planet;

public class Main {
    public static void main(String[] args) {
        Homosepian homosepian = new Homosepian("Nadav");
        Neanderthal neanderthal = new Neanderthal("Yehuda");
        Homoerectus homoerectus = new Homoerectus("Tal");
        Homoebolis homoebolis = new Homoebolis("Sapir");
        Homofloresiensis homofloresiensis = new Homofloresiensis("Or");
        Continent asia = new Continent("Asia");
        Continent africa = new Continent("Africa");
        asia.addTribeToContinent(homosepian);
        asia.addTribeToContinent(neanderthal);
        africa.addTribeToContinent(homoerectus);
        africa.addTribeToContinent(homoebolis);
        asia.addTribeToContinent(homofloresiensis);
        Planet planet = Planet.getInstance();
        planet.addContinentToPlanet(asia);
        planet.addContinentToPlanet(africa);
        planet.runPlanetCycle();
    }
}
