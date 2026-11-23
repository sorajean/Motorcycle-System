package motorcycletaxi.Data;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import motorcycletaxi.model.Driver;
import motorcycletaxi.model.Location;

public class LocationData {

    // town -> list of barangays (LinkedHashMap keeps the order)
    private static final Map<String, List<String>> TOWNS = new LinkedHashMap<>();
    // town -> {latitude, longitude} of the town center
    private static final Map<String, double[]> CENTERS = new LinkedHashMap<>();

    static {
        TOWNS.put("San Juan", Arrays.asList(
            "Sua", "Aguinaldo", "Benit", "Cabalian", "Capacuhan", "Catmon",
            "Causwagan", "Dayanog", "Garrido", "Pasil", "Pong-oy", "Santa Cruz",
            "Somoje", "Tabunan", "Timba", "Upper Santa Cruz", "Villa Hermosa", "Poblacion"));

        TOWNS.put("Saint Bernard", Arrays.asList(
            "Atuyan", "Ayahag", "Bantawon", "Bolodbolod", "Cabagawan",
            "Carnaga", "Catmon", "Guinsaugon", "Himatagon", "Himbangan",
            "Himos-onan", "Hinabian", "Hindag-an", "Kauswagan", "Libas",
            "Lipanto", "Magatas", "Magbagacay", "Mahayag", "Mahayahay",
            "Malibago", "Malinao", "Nueva Esperanza", "Panian", "San Isidro",
            "Santa Cruz", "Sug-angon", "Tabontabon", "Tambis I", "Tambis II"));

        TOWNS.put("Anahawan", Arrays.asList(
            "Amagusan", "Calintaan", "Canlabian", "Lower Poblacion", "Mahayahay",
            "Poblacion", "San Isidro", "San Vicente", "Sta. Cruz", "Tagup-on",
            "Tuburan", "Upper Poblacion", "Calubian", "Kauswagan"));

        CENTERS.put("San Juan", new double[]{10.26, 125.20});
        CENTERS.put("Saint Bernard", new double[]{10.30, 125.14});
        CENTERS.put("Anahawan", new double[]{10.19, 125.17});
    }

    public static List<String> getTowns() {
        return new ArrayList<>(TOWNS.keySet());
    }

    public static List<String> getBarangays(String town) {
        return TOWNS.get(town);
    }

    // Builds a Location with simulated coordinates:
    // town center + a fixed small offset based on the barangay name.
    public static Location getLocation(String town, String barangay) {
        String s = town + barangay;
        int h = 0;
        for (int i = 0; i < s.length(); i++) {
            h += s.charAt(i) * (i + 1);
        }
        h = h % 9973;
        double[] c = CENTERS.get(town);
        double lat = c[0] + ((h % 80) - 40) / 1000.0;
        double lon = c[1] + (((h * 7) % 80) - 40) / 1000.0;
        return new Location(town, barangay, lat, lon);
    }

    // Preloaded drivers. Add or edit drivers here.
    public static List<Driver> createDrivers() {
        List<Driver> drivers = new ArrayList<>();
        drivers.add(new Driver(1, "Mario Mendoza", "SL-043", getLocation("Saint Bernard", "San Isidro")));
        drivers.add(new Driver(2, "Jose Mendoza", "SL-042", getLocation("Saint Bernard", "Panian")));
        drivers.add(new Driver(3, "Jose Santos", "SL-022", getLocation("Saint Bernard", "Bolodbolod")));
        drivers.add(new Driver(4, "Nestor Garcia", "SL-038", getLocation("Saint Bernard", "Mahayahay")));
        drivers.add(new Driver(5, "Pedro Mendoza", "SL-041", getLocation("Saint Bernard", "Nueva Esperanza")));
        drivers.add(new Driver(6, "Ramon Cruz", "SL-010", getLocation("San Juan", "Sua")));
        drivers.add(new Driver(7, "Luis Tan", "AN-005", getLocation("Anahawan", "Poblacion")));
        return drivers;
    }
}