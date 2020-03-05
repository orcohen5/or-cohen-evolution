package Places;

import Organisms.Organism;
import Utilities.ExecutorServiceUtil;
import Utilities.LoggerUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.TimerTask;
import java.util.concurrent.ExecutorService;

public class Planet extends TimerTask {
    private static Planet instance = null;
    private List<Continent> continents;
    private ExecutorService executor;
    private List<Organism> fightingOrganisms;
    private List<String> continentsNames;
    private Organism attacker;
    private Organism defender;
    private String attackingContinentName;
    private String defendingContinentName;
    private String attackerName;
    private String defenderName;
    private int numberOfOrganisms;

    private Planet(List<Continent> continents) {
        notifyStart();
        this.continents = continents;
        this.executor = ExecutorServiceUtil.getExecutor();
    }

    public static Planet getInstance(List<Continent> continents) {

        if(instance == null) {
            instance = new Planet(continents);
        }
        return instance;
    }

    private List<Organism> getOrganismsInContinent(Continent continent) {
        return continent.getOrganisms();
    }

    public void addContinents(List<Continent> continentsList) {

        for(Continent continent : continentsList) {
            continents.add(continent);
        }
    }

    public void addContinent(Continent continent) {
        continents.add(continent);
    }

    public void run() {
        startLifeCycle();
    }

    private void notifyStart() {
        String startMessage = "Planet Started!";
        LoggerUtil.logData(startMessage);
    }

    private void startLifeCycle() {
        LoggerUtil.logData("______________");
        startFightBetweenContinentsWinners();

        for(int i = 0; i < continents.size(); i++) {
            executor.execute(continents.get(i));
        }
    }

    private void startFightBetweenContinentsWinners() {
        numberOfOrganisms = 0;
        fightingOrganisms = new ArrayList();
        continentsNames = new ArrayList();

        for(Continent attackingContinent : continents) {

            for(Continent defendingContinent : continents) {

                if(isOnlyOneOrganismInEachContinent(attackingContinent, defendingContinent)) {
                    attacker = getOrganismsInContinent(attackingContinent).get(0);
                    defender = getOrganismsInContinent(defendingContinent).get(0);
                    attackingContinentName = attackingContinent.getContinentName();
                    defendingContinentName = defendingContinent.getContinentName();
                    attackerName = attacker.getOrganismName();
                    defenderName = defender.getOrganismName();

                    if(attacker.getSumOfProperties() - defender.getSumOfProperties() > 5) {
                        LoggerUtil.logData("Continent War Attacker -> " + attacker.toString() + " from " + attackingContinent.getContinentName());
                        LoggerUtil.logData("Continent War Defender -> " + defender.toString() + " from " + defendingContinent.getContinentName());

                        if(attacker.getBalance() < (defender.getBalance() / 2)) {
                            attacker.setBalance(0);
                            attackingContinent.removeOrganism(attacker);
                        }
                        else {
                            attacker.setBalance(attacker.getBalance() - (defender.getBalance() / 2));
                        }
                        defendingContinent.removeOrganism(defender);
                        logFightData(attackingContinent);
                    }
                }
            }
        }

        for(Continent continent : continents) {

            if(continent.getOrganisms().size() != 0) {
                numberOfOrganisms += getOrganismsInContinent(continent).size();
                fightingOrganisms.addAll(getOrganismsInContinent(continent));
                continentsNames.add(continent.getContinentName());
            }
        }

        if(isPlanetHasToBeStopped())
            stopPlanet();
    }

    private boolean isOnlyOneOrganismInEachContinent(Continent first, Continent second) {
        return getOrganismsInContinent(first).size() == 1 && getOrganismsInContinent(second).size() == 1;
    }

    private void logFightData(Continent attackingContinent) {
        String fightData = attackingContinentName + ":" + attackerName + " attack " +
                defendingContinentName + ":" + defenderName +
                "-> result = ";

        if(getOrganismsInContinent(attackingContinent).size() == 0)
            LoggerUtil.logData(fightData + "Tie");
        else
            LoggerUtil.logData(fightData + attackingContinentName + ":" + attackerName + " wins");
    }

    private boolean isPlanetHasToBeStopped() {
        return numberOfOrganisms == 1 || numberOfOrganisms == 0;
    }

    private void stopPlanet() {

        if(numberOfOrganisms == 1) {
            LoggerUtil.logData("Winner ->" + continentsNames.get(0) + " -> " + fightingOrganisms.get(0).toString());
            fightingOrganisms.remove(fightingOrganisms.get(0));
            continentsNames.remove(continentsNames.get(0));
            numberOfOrganisms--;
        }
        LoggerUtil.logData("Planet Stopped!");
        System.exit(0);
    }
}
