package al420445;

import al420445.dao.AirportDaoImpl;
import al420445.service.AirportService;

public class MainExempleAvecCouches {
    public static void main(String[] args) {
        final AirportService airportService = new AirportService(new AirportDaoImpl());
        airportService.addPassenger("Loki", 2);
        System.out.println(airportService.findPassengersByName("Loki"));

    }
}
