package model.DAO;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class OperationDAO {

    private static EntityManagerFactory emf;
    private static EntityManager em;

    public OperationDAO() {
        emf = Persistence.createEntityManagerFactory("jpa-persistence-unit-1");
        em = emf.createEntityManager();
    }

    public void saveOperation(Operation operation) {
        em.getTransaction().begin();
        em.persist(operation);
        em.getTransaction().commit();
    }

    public static void close() {
        em.close();
        emf.close();
    }
}
