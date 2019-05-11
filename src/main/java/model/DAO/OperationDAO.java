package model.DAO;

import EncDec.FileEncryption;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.exception.JDBCConnectionException;
import org.hibernate.service.spi.ServiceException;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;
import java.util.List;

/**
 * Handles the persistence.
 */
public class OperationDAO {

    /**
     * <code>Logger</code> instance for logging.
     */
    private static Logger logger = LogManager.getLogger(FileEncryption.class);

    private static EntityManagerFactory emf;
    private static EntityManager em;

    /**
     * Initializes the DAO.
     */
    public OperationDAO() {
        try {
            emf = Persistence.createEntityManagerFactory("jpa-persistence-unit-1");
            em = emf.createEntityManager();
        } catch (ServiceException e) {
            logger.warn("Connection couldn't be established. Exception: " + e);
        }
    }

    /**
     * Saves the operation on the database.
     *
     * @param operation the operation to be saved
     */
    public void saveOperation(Operation operation) {
        try {
            em.getTransaction().begin();
            em.persist(operation);
            em.getTransaction().commit();
        } catch (NullPointerException e) {
            logger.warn("Connection not found. Exception: " + e);
            emf = Persistence.createEntityManagerFactory("jpa-persistence-unit-1");
            em = emf.createEntityManager();
        } catch (JDBCConnectionException e) {
            logger.warn("Connection not found. Exception: " + e);
        }
    }

    /**
     * Reads the operations of the given operation type from the database.
     *
     * @param operationType the operation type to be read
     * @return a <code>List</code> of operations
     */
    public List<Operation> readOperationHistory(String operationType) {
        TypedQuery<Operation> query;
        try {
            query = em.createQuery("SELECT o FROM Operation o WHERE o.operationType'" + operationType + "'", Operation.class);
        } catch (NullPointerException e) {
            logger.warn("Connection not found. Exception: " + e);
            emf = Persistence.createEntityManagerFactory("jpa-persistence-unit-1");
            em = emf.createEntityManager();
            return null;
        }

        return query.getResultList();
    }

    /**
     * Reads the operations of the given file from the database.
     *
     * @param path the file to be read
     * @return a <code>List</code> of operations
     */
    public List<Operation> readFileHistory(String path) {
        TypedQuery<Operation> query;
        try {
            query = em.createQuery("SELECT o FROM Operation o WHERE o.path'" + path + "'", Operation.class);
        } catch (NullPointerException e) {
            logger.warn("Connection not found. Exception: " + e);
            emf = Persistence.createEntityManagerFactory("jpa-persistence-unit-1");
            em = emf.createEntityManager();
            return null;
        }

        return query.getResultList();
    }

    /**
     * Closes the entity.
     */
    public static void close() {
        try {
            em.close();
            emf.close();
        } catch (NullPointerException e) {
            logger.warn("Connection not found. Exception: " + e);
        }
    }
}
