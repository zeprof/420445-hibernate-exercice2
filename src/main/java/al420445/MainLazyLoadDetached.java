package al420445;

import al420445.airport.Passenger;
import al420445.airport.Ticket;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import java.sql.SQLException;

import static java.lang.Thread.currentThread;

public class MainLazyLoadDetached {
    public static void main(String[] args) throws SQLException, InterruptedException {
        TcpServer.createTcpServer();

        EntityManagerFactory emf = Persistence.createEntityManagerFactory("hibernate2.ex1");
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();

        MainExemple1.insertDataInDb(emf);
        em.getTransaction().commit();
        em.close();

        em = emf.createEntityManager();
        em.getTransaction().begin();
        final Passenger passenger = em.find(Passenger.class, 2);
        em.getTransaction().commit();
        em.close();
        // Les tickets sont toujours lazy Load, mais ici le EntityManager a été fermé
        // Il n'y a donc plus de PersistentContext
        // passenger est DETACHED
        // Et comme les tickets sont Lazy Load, il n'y a plus de sessions pour aller les chercher
        // à la bd
        // Voir la console
        for (Ticket ticket: passenger.getTickets()) {
            System.out.println(ticket.getNumber());
        }


        currentThread().join();
    }
}
