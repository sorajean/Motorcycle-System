package motorcycletaxi.service;

import java.time.LocalTime;

public class FareCalculator {
    public static final double BASE_FARE = 30;         // pesos
    public static final double RATE_PER_KM = 10;       // pesos per km
    public static final double NIGHT_SURCHARGE = 0.20; // +20% from 8pm to 5am

    public static boolean isNight(LocalTime time) {
        return time.getHour() >= 20 || time.getHour() < 5;
    }

    public static String getFareType(LocalTime time) {
        return isNight(time) ? "Night" : "Regular";
    }

    // Example: 9.8 km -> 30 + (10 x 9.8) = P128.00
    public static double computeFare(double km, LocalTime time) {
        double fare = BASE_FARE + RATE_PER_KM * km;
        if (isNight(time)) {
            fare = fare * (1 + NIGHT_SURCHARGE);
        }
        return Math.round(fare * 100.0) / 100.0;
    }
}
