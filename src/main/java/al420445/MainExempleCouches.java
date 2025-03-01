package al420445;

import al420445.dao.AirportDao;
import al420445.dao.AirportDaoImpl;
import al420445.service.AirportService;

public class MainExempleCouches {
    public static void main(String[] args) {
        AirportDao dao = new AirportDaoImpl();
        System.out.println(new AirportService(dao).getAirports());
    }
}
