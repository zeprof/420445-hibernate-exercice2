package al420445;

import al420445.dao.BebelleDTO;
import al420445.utils.TableGeneratorCli;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class MainExempleDtoCustom {
    public static void main(String[] args) throws InterruptedException, SQLException {
        TcpServer.createTcpServer();
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("hibernate2.ex1");
        MainExemple1.insertDataInDb(emf);

        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();

        // Voici un exemple qui montre comment utilisé un Dto directement dans une projection JPQL
        // Notez le 'new al420445.dao.BebelleDTO' dans la projection
        // Ceci va dire à l'ORM de mettre le résultat de la requête dans ce DTO, en fait, dans une
        // List<BebelleDTO>
        var queryStr = """
                        select new al420445.dao.BebelleDTO(count(t), t.passenger) 
                        from Ticket t 
                        group by t.passenger
                """;
        var tableGenerator = new TableGeneratorCli();
        List<String> headersList = new ArrayList<>();
        headersList.add("Passenger");
        headersList.add("Tickets count");
        List<List<String>> rowsList = new ArrayList<>();
        final List<BebelleDTO> bebelleDTOS = em.createQuery(
                queryStr, BebelleDTO.class).getResultList();
        bebelleDTOS.forEach(o -> {
            List<String> row = new ArrayList<>();
            row.add(o.passenger().getName());
            row.add(Long.toString(o.count()));
            rowsList.add(row);
        });
        System.out.println(tableGenerator.generateTable(headersList, rowsList));
        // On voit ici le nombre de billets pour chaque passager

        em.getTransaction().commit();
        em.close();
        emf.close();

        //System.out.println(airport.getPassengers().size());

        Thread.currentThread().join();
    }
}
