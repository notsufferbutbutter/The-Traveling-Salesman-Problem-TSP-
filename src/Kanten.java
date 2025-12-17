public class Kanten implements Comparable<Kanten> {
    protected City startCity; // Startknoten der Kante
    City endCity; // Endknoten der Kante
    double distance;
    double pheromone; //Pheromonwert t_ij

    private static final double INITIAL_PHEROMONE = 0.1;

    public Kanten(City startCity, City endCity) {
        this.startCity = startCity; // Startknoten setzen
        this.endCity = endCity; // Endknoten setzen
        this.distance = calculateDistance(startCity, endCity); // Gewicht setzen
        this.pheromone = INITIAL_PHEROMONE;
    }

    private double calculateDistance(City c1, City c2) {
        double dx = c1.getX() - c2.getX();
        double dy = c1.getY() - c2.getY();
        return Math.sqrt(dx * dx + dy * dy);
    }

    public double getDistance() {
        return distance;
    }

    public double getPheromone() {
        return pheromone;
    }

    public void setPheromone(double pheromone) {
        this.pheromone = pheromone;
    }

    public City getOtherCity(City city) {
        if (city.equals(startCity)) { // Prüfen, ob über Startknoten
            return endCity; // Nachbarknoten zurückgeben
        } else if (city.equals(endCity)) { // Prüfen, ob über Endknoten
            return startCity; // Nachbarknoten zurückgeben
        }
        return null; // Knoten gehört nicht zu dieser Kante
    }

    public int compareTo(Kanten other) {
        return Double.compare(this.distance, other.distance); // Vergleichen nach Gewicht
    }
}