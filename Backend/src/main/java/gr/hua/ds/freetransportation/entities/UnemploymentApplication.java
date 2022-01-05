package gr.hua.ds.freetransportation.entities;

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
    @Column(length = 255, nullable = true)
    private String photo;
    @Column(length = 15, nullable = false)
    private String status = "PENDING";

    @ManyToOne(fetch = FetchType.EAGER)
    private User user = new User();

    public UnemploymentApplication() {
    }

    public UnemploymentApplication(String reason, String photo, User user) {
        this.dateSubmitted = new Date();
        this.reason = reason;
        this.photo = photo;
        this.status = "PENDING";
        this.user = user;
    }

    public UnemploymentApplication(String reason, String photo) {
        this.dateSubmitted = new Date();
        this.reason = reason;
        this.photo = photo;
        this.status = "PENDING";
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

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
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
}
