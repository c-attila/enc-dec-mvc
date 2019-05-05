package model.DAO;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;
import java.util.List;

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

    public List<Operation> readOperationHistory(String operationType) {
        TypedQuery<Operation> query = em.createQuery("SELECT o FROM Operation o WHERE o.operationType'" + operationType + "'", Operation.class);

        return query.getResultList();
    }

    public List<Operation> readFileHistory(String path) {
        TypedQuery<Operation> query = em.createQuery("SELECT o FROM Operation o WHERE o.path'" + path + "'", Operation.class);

        return query.getResultList();
    }

    public static void close() {
        em.close();
        emf.close();
    }
}
