import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Graph {

    protected List<City> cities = new ArrayList<>(); // Liste aller Knoten
    protected List<Kanten> kanten = new ArrayList<>(); // Liste aller Kanten
    private Map<String, Kanten> kantenMap = new HashMap<>();

    public void addCity(int id, int x, int y) {
        cities.add(new City(id, x, y)); // Neuen Stadt mit ID hinzufügen
    }

    public void addKante(City startCity, City endCity) {
        Kanten k = new Kanten(startCity, endCity); // Neue Kante erstellen
        kanten.add(k); // Kante zur Graphliste hinzufügen
        String key = getKanteKey(startCity, endCity);
        kantenMap.put(key, k);

    }

    private String getKanteKey(City startCity, City endCity) {
        int id1 = startCity.getId();
        int id2 = endCity.getId();
        return (id1< id2) ? (id1 + "-" + id2) : (id2 + "-" + id1);
    }

    public List<City> getCities() {
        return this.cities;
    }

    public List<Kanten> getKanten() {
        return this.kanten; // Alle Kanten zurückgeben
    }

    public Kanten getKanten(City startCity, City endCity) {
        String key = getKanteKey(startCity, endCity);
        return kantenMap.get(key);
    }

    public void createAllKanten(){
        this.kanten.clear();
        this.kantenMap.clear();

        for(int i = 0; i < cities.size(); i++){
            for(int j = i + 1; j < cities.size(); j++){
                addKante(cities.get(i), cities.get(j));
            }
        }
    }
}


