package model.DAO;

import javax.persistence.*;
import java.sql.Timestamp;

/**
 * An <code>object</code> that represents operations.
 */
@Entity
@Table(name = "Operation")
public class Operation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    /**
     * An operation that was made. Can be file encryption, file decryption or RSA key generation.
     */
    @Column(name = "Operation_type")
    private String operationType;

    /**
     * The file on which the operation was made.
     * - symbol if the operation was key generation.
     */
    @Column(name = "File")
    private String path;

    /**
     * The date and time of the operation.
     */
    @Column(name = "Date")
    private java.sql.Timestamp date;

    public Operation(String operationType, String path, Timestamp date) {
        this.operationType = operationType;
        this.path = path;
        this.date = date;
    }

    public Operation() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getOperationType() {
        return operationType;
    }

    public void setOperationType(String operationType) {
        this.operationType = operationType;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public Timestamp getDate() {
        return date;
    }

    public void setDate(Timestamp date) {
        this.date = date;
    }
}
