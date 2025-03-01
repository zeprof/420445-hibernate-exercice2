package al420445.dao;

import al420445.airport.Airport;
import al420445.airport.Passenger;

import jakarta.persistence.*;
import java.util.List;

public class AirportDaoImpl implements AirportDao {
    public List<Airport> getAirports() {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("hibernate2.ex1");
        EntityManager em = emf.createEntityManager();

        em.getTransaction().begin();

        List<Airport> airports = em.createQuery("select airport from Airport airport", Airport.class).getResultList();

        em.getTransaction().commit();

        em.close();
        emf.close();

        return airports;
    }

    public void addPassenger(String name, int airportId) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("hibernate2.ex1");
        EntityManager em = emf.createEntityManager();

        em.getTransaction().begin();

        final Query query = em.createQuery("select p from Passenger p where :name = name");
        query.setParameter("name", name);
        final List passengers = query.getResultList();
        if (passengers.isEmpty()) {
            em.persist(new Passenger(name));
        }


        em.getTransaction().commit();

        em.close();
        emf.close();
    }

    @Override
    public List<Passenger> findPassengersByName(String passengersByName) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("hibernate2.ex1");
        EntityManager em = emf.createEntityManager();

        em.getTransaction().begin();

        TypedQuery<Passenger> queryPassenger = em.createQuery("select p from Passenger p where lower(p.name) like ?1", Passenger.class);
        queryPassenger.setParameter(1, passengersByName.toLowerCase() + "%");

        em.getTransaction().commit();
        List<Passenger> resultList = queryPassenger.getResultList();
        em.close();
        emf.close();
        return resultList;
    }
}
