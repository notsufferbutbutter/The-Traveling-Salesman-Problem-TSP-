import java.util.List;
import java.util.Random;
public class SimulationController {

    private int currentNumCities;
    private Graph graph;
  
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
        Random rand = new Random();
        for (int i = 0; i < numCities; i++) {
            int x = rand.nextInt(width - 40) + 20;
            int y = rand.nextInt(height - 40) + 20;
            graph.addCity(i + 1, x, y);
        }
        graph.createAllKanten();
        view.redraw();
    }


  
    // --- Getter für die Visualisierung ---

    public List<City> getCities() {
        return graph.getCities();
    }

    public List<Kanten> getEdges() {
        return graph.getKanten();
    }

}
