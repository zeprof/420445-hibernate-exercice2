package al420445;

import al420445.airport.Passenger;
import al420445.airport.Ticket;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import java.sql.SQLException;

public class MainLazyLoadManaged {
    public static void main(String[] args) throws InterruptedException, SQLException {
        TcpServer.createTcpServer();

        EntityManagerFactory emf = Persistence.createEntityManagerFactory("hibernate2.ex1");
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();

        final MainExemple1.Result result = MainExemple1.insertDataInDb(emf);
        em.getTransaction().commit();
        em.close();

        em = emf.createEntityManager();
        em.getTransaction().begin();
        final Passenger passenger = em.find(Passenger.class, 2);
        // Comme le EntityManager n'est pas fermé, on peut accéder aux tickets
        // passenger est MANAGED
        // Ne pas oublier que passenger est 'LAZY LOAD'
        for (Ticket ticket: passenger.getTickets()) {
            System.out.println(ticket.getNumber());
        }

        em.getTransaction().commit();
        em.close();

        Thread.currentThread().join();
    }
}
