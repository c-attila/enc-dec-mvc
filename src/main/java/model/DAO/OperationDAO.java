package model.DAO;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;
import java.util.List;

/**
 * Handles the persistence.
 */
public class OperationDAO {

    private static EntityManagerFactory emf;
    private static EntityManager em;

    /**
     * Initializes the DAO.
     */
    public OperationDAO() {
        emf = Persistence.createEntityManagerFactory("jpa-persistence-unit-1");
        em = emf.createEntityManager();
    }

    /**
     * Saves the operation on the database.
     *
     * @param operation the operation to be saved
     */
    public void saveOperation(Operation operation) {
        em.getTransaction().begin();
        em.persist(operation);
        em.getTransaction().commit();
    }

    /**
     * Reads the operations of the given operation type from the database.
     *
     * @param operationType the operation type to be read
     * @return a <code>List</code> of operations
     */
    public List<Operation> readOperationHistory(String operationType) {
        TypedQuery<Operation> query = em.createQuery("SELECT o FROM Operation o WHERE o.operationType'" + operationType + "'", Operation.class);

        return query.getResultList();
    }

    /**
     * Reads the operations of the given file from the database.
     *
     * @param path the file to be read
     * @return a <code>List</code> of operations
     */
    public List<Operation> readFileHistory(String path) {
        TypedQuery<Operation> query = em.createQuery("SELECT o FROM Operation o WHERE o.path'" + path + "'", Operation.class);

        return query.getResultList();
    }

    /**
     * Closes the entity.
     */
    public static void close() {
        em.close();
        emf.close();
    }
}
