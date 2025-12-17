import javax.swing.*;
import java.util.List;
import java.util.Random;


public class SimulationController {

    private int currentNumCities;
    private Graph graph;

    private AntColonyOptimization aco;
    private Timer timer;


    // Für die Simulation/Visualisierung
    private SimulationView view; // Die Klasse, die zeichnet (z.B. ein JPanel)


    public SimulationController(SimulationView view) {
        this.view = view;
        this.graph = new Graph();
    }

    public void setView(SimulationView view) {
        this.view = view;
    }


    // --- Zufällige Städte generieren ---
    public void generateRandomCities(int numCities, int width, int height) {
       currentNumCities = numCities;
        graph.getCities().clear();
        Random rand = new Random(1142);
        for (int i = 0; i < numCities; i++) {
            int x = rand.nextInt(width - 40) + 20;
            int y = rand.nextInt(height - 40) + 20;
            graph.addCity(i + 1, x, y);
        }
        graph.createAllKanten();
        view.redraw();
    }

    public void startOptimization(int ants, double alpha, double beta, double rho) {
        aco = new AntColonyOptimization(graph, ants, alpha, beta, rho);

        timer = new Timer(2, e -> {
            aco.runOneIteration();
            view.redraw();
        });
        timer.start();
    }




    // --- Getter für die Visualisierung ---

    public List<City> getCities() {
        return graph.getCities();
    }

    public List<Kanten> getEdges() {
        return graph.getKanten();
    }

}
