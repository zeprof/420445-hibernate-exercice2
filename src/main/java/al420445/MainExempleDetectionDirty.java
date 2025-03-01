package al420445;

import al420445.airport.Passenger;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

public class MainExempleDetectionDirty {
    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("hibernate2.ex1");

        insertPassenger(emf);
        queryPassenger(emf);

        emf.close();
    }

    private static void insertPassenger(EntityManagerFactory emf) {
        try(EntityManager em = emf.createEntityManager()) {

            em.getTransaction().begin();

            final Passenger p = new Passenger("Cedric");
            em.persist(p);

            em.getTransaction().commit();
            System.out.println(p);


        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    private static void queryPassenger(EntityManagerFactory emf) {
        try(EntityManager em = emf.createEntityManager()) {

            em.getTransaction().begin();

            final Passenger p2 = em.find(Passenger.class, Integer.valueOf(1));
            System.out.println(p2);
            // Comme p2 se trouve à l'intérieur du PersistentContext (l'entityManager n'a pas encore été 'close')
            // Tout changement effectué à p2 sera automatiquement propagé à la BD lors du commit
            // À noter qu'il n'y a aucun 'UPDATE' dans le code
            p2.setName("Manolo");

            em.getTransaction().commit();

        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        // On vérifie que le changement a bien eu lieu dans la bd en allant le chercher à nouveau dans un
        // nouveau PersistentContext
        System.out.println("Après le changement, on devrait voir Manolo maintenant");
        try(EntityManager em = emf.createEntityManager()) {

            em.getTransaction().begin();

            final Passenger p2 = em.find(Passenger.class, Integer.valueOf(1));
            System.out.println(p2);  // Devrait écrire Manolo

            em.getTransaction().commit();

            // Voir l'impression dans la console

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
