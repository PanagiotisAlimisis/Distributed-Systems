package gr.hua.ds.freetransportation.entities;

import gr.hua.ds.freetransportation.Status;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "UNEMPLOYMENT_APPLICATIONS")
public class UnemploymentApplication {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(name = "DATE_SUBMITTED", nullable = false)
    private Date dateSubmitted = new Date();
    @Column(length = 500, nullable = true)
    private String reason;
    @Column(length = 15, nullable = false)
    private String status = Status.PENDING.toString();

    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.DETACH)
    private User user = new User();

    public UnemploymentApplication() {
    }

    public UnemploymentApplication(String reason, User user) {
        this.dateSubmitted = new Date();
        this.reason = reason;
        this.status = Status.PENDING.toString();
        this.user = user;
    }

    public UnemploymentApplication(String reason) {
        this.dateSubmitted = new Date();
        this.reason = reason;
        this.status = Status.PENDING.toString();
    }

    public Integer getId() {
        return this.id;
    }

    public Date getDateSubmitted() {
        return dateSubmitted;
    }

    public void setDateSubmitted(Date dateSubmitted) {
        this.dateSubmitted = dateSubmitted;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @Override
    public String toString() {
        return "UnemploymentApplication{" +
                "id=" + id +
                ", dateSubmitted=" + dateSubmitted +
                ", reason='" + reason + '\'' +
                ", status='" + status + '\'' +
                ", user=" + user.toString() +
                '}';
    }
}
