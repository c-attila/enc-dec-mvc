package model.DAO;

import javax.persistence.*;
import java.sql.Timestamp;

/**
 * An <code>object</code> that represents an operation.
 */
@Entity
@Table(name = "Operation")
public class Operation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    /**
     * An operation that was done. Can be file encryption, file decryption or rsa key generation.
     */
    @Column(name = "operationType")
    private String operationType;

    /**
     * The file on which the operation was done.
     * - symbol if the operation was key generation.
     */
    @Column(name = "file")
    private String path;

    /**
     * The date and time of the operation.
     */
    @Column(name = "date")
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
