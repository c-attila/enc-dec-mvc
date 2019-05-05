package model.DAO;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
@Table(name = "Operation")
public class Operation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "Operation_type")
    private String operationType;

    @Column(name = "File")
    private String path;

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
