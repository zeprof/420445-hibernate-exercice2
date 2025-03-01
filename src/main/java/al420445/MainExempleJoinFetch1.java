package al420445;

import al420445.airport.Airport;

import al420445.airport.Passenger;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import jakarta.persistence.TypedQuery;

import java.sql.SQLException;
import java.util.List;

public class MainExempleJoinFetch1 {
    public static void main(String[] args) throws InterruptedException, SQLException {
        TcpServer.createTcpServer();
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("hibernate2.ex1");
        MainExemple1.insertDataInDb(emf);

        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();

        final TypedQuery<Passenger> pass =
                em.createQuery(
            "select p from Passenger p where lower(p.airport.name) like 'henri%'", Passenger.class);
        final List<Passenger> passengers = pass.getResultList();
        System.out.println(passengers);

        // Dans cet exemple, on fait un 'join fetch' afin de populer immédiatement
        // la collection de 'passengers' dans Airport.java
        // Notez la projection qui indique seulement 'a' (l'alias utilisé pour Airport)  Cela veut dire que l'on veut
        // avoir une instance hydratée d'Airport.  Le 'getSingleResult()' nous le retoune à la ligne 38
        String qlString1 = "select a from Airport a join fetch a.passengers where lower(a.name) like 'henri%'";
        String qlString2 = "select a from Airport a where lower(a.name) like 'henri%'";
        final TypedQuery<Airport> airportQuery =
                em.createQuery(qlString1, Airport.class);
        final Airport airport = airportQuery.getSingleResult();

        // Ici on détache notre instance 'airport' qui est dans le Persistence Context
        em.detach(airport);

        // Comme la collection 'passengers' fait partie du toString() de Airport, l'exécution du programme
        // va essayer d'imprimer tous les 'passengers' de 'Airport'
        // Ça va passer ici puisqu'on a fait le 'join fetch' plus haut à la ligne 32
        System.out.println(airport);
        System.out.println(airport.getPassengers());

        em.getTransaction().commit();
        em.close();
        emf.close();

        //System.out.println(airport.getPassengers().size());

        Thread.currentThread().join();
    }
}
