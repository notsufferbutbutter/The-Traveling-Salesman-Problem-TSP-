import java.util.*;

public class AntColonyOptimization {

    private final Graph graph;
    private final Random random = new Random();

    private int numAnts;
    private double alpha;
    private double beta;
    private double rho;
    private double q = 100;

    private List<City> bestTour = null;
    private double bestTourLength = Double.MAX_VALUE;
    private int iteration = 0;


    public AntColonyOptimization(Graph graph,
                                 int numAnts,
                                 double alpha,
                                 double beta,
                                 double rho) {
        this.graph = graph;
        this.numAnts = numAnts;
        this.alpha = alpha;
        this.beta = beta;
        this.rho = rho;
    }

    public void runOneIteration() {
        iteration++;

        List<List<City>> allTours = new ArrayList<>();
        List<Double> allLengths = new ArrayList<>();

        System.out.println("===== Iteration " + iteration + " =====");

        for (int i = 0; i < numAnts; i++) {
            City start = graph.getCities().get(i % graph.getCities().size());
            List<City> tour = constructTour(start);
            double length = calculateTourLength(tour);

            allTours.add(tour);
            allLengths.add(length);

            // print this ant's tour
            System.out.print("Ant " + (i + 1) + ": ");
            for (City city : tour) {
                System.out.print(city.getId() + " -> ");
            }
            System.out.println(tour.get(0).getId() +
                    " | Length = " + String.format("%.2f", length));

            if (length < bestTourLength) {
                bestTourLength = length;
                bestTour = new ArrayList<>(tour);
            }
        }

        System.out.println("Best so far: " +
                String.format("%.2f", bestTourLength));
        System.out.println();

        evaporatePheromones();
        updatePheromones(allTours, allLengths);
    }


    public List<City> getBestTour() {
        return bestTour;
    }

    private List<City> constructTour(City start) {
        List<City> tour = new ArrayList<>();
        Set<City> visited = new HashSet<>();

        City current = start;
        tour.add(current);
        visited.add(current);

        while (visited.size() < graph.getCities().size()) {
            City next = selectNextCity(current, visited);
            tour.add(next);
            visited.add(next);
            current = next;
        }

        return tour;
    }

    private City selectNextCity(City current, Set<City> visited) {
        double sum = 0;
        Map<City, Double> probabilities = new HashMap<>();

        for (City city : graph.getCities()) {
            if (!visited.contains(city)) {
                Kanten edge = graph.getKanten(current, city);

                double pheromone = Math.pow(edge.getPheromone(), alpha);
                double visibility = Math.pow(1.0 / edge.getDistance(), beta);
                double value = pheromone * visibility;

                probabilities.put(city, value);
                sum += value;
            }
        }

        // since we are dealing with probabilty , which is a number between 0 and 1
        //a roulette-wheel selection is implemented using rand * sum instead of explicitly dividing by the sum — mathematically equivalent.
        double r = random.nextDouble() * sum;
        double cumulative = 0;

        for (Map.Entry<City, Double> entry : probabilities.entrySet()) {
            cumulative += entry.getValue();
            if (cumulative >= r) {
                // return the next city
                return entry.getKey();
            }
        }

        // for some reason a value isnt found, but this should never happen
        return probabilities.keySet().iterator().next(); // fallback
    }

    //evaporated to mimic real life of when no ant is actively there
    private void evaporatePheromones() {
        for (Kanten edge : graph.getKanten()) {
            edge.setPheromone(edge.getPheromone() * (1 - rho));
        }
    }

    //once ants walk by , add more
    private void updatePheromones(List<List<City>> tours, List<Double> lengths) {
        for (int i = 0; i < tours.size(); i++) {
            List<City> tour = tours.get(i);
            double contribution = q / lengths.get(i);

            for (int j = 0; j < tour.size() - 1; j++) {
                Kanten edge = graph.getKanten(tour.get(j), tour.get(j + 1));
                edge.setPheromone(edge.getPheromone() + contribution);
            }

            // return to start
            Kanten closingEdge = graph.getKanten(
                    tour.get(tour.size() - 1),
                    tour.get(0)
            );
            closingEdge.setPheromone(closingEdge.getPheromone() + contribution);
        }
    }

    private double calculateTourLength(List<City> tour) {
        double length = 0;

        for (int i = 0; i < tour.size() - 1; i++) {
            length += graph.getKanten(tour.get(i), tour.get(i + 1)).getDistance();
        }

        length += graph.getKanten(
                tour.get(tour.size() - 1),
                tour.get(0)
        ).getDistance();

        return length;
    }


}
