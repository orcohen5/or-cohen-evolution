import Events.EvolutionManager;

public class Main {
    public static void main(String[] args) {
        EvolutionManager evolutionManager = new EvolutionManager();
        evolutionManager.initializeEvolution();
        evolutionManager.startEvolution();
    }
}
