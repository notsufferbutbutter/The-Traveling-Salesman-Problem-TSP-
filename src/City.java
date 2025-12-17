
public class City {
    int id; // eindeutige Kennung des Knotens
    int x;
    int y;
    //List<Kanten> kantenDurchKnoten; // Liste aller Kanten, die durch diesen Knoten verlaufen

    public City(int id, int x, int y) {
        this.id = id; // Knoten-ID setzen
        //this.kantenDurchKnoten = new ArrayList<>(); // Liste für Kanten initialisieren
        this.x = x;
        this.y = y;
    }
    public int getId() {return id;}
    public int getX() {return x;}
    public int getY() {return y;}

    @Override
    public boolean equals(Object o) {
        if (this == o) return true; // gleiche Referenz -> gleich
        if (!(o instanceof City)) return false; // falscher Typ -> nicht gleich
        City city = (City) o;
        return this.id == city.id; // gleich, wenn die IDs gleich sind
    }

    @Override
    public int hashCode() {
        return Integer.hashCode(id); // Hash basierend auf ID
    }
}
