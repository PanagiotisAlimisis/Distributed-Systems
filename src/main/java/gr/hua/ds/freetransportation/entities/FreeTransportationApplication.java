package gr.hua.ds.freetransportation.entities;

import gr.hua.ds.freetransportation.Status;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "FREE_TRANSPORTATION_APPLICATIONS")
public class FreeTransportationApplication {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(name = "DATE_SUBMITTED", nullable = false)
    private Date dateSubmitted = new Date();
    @Column(length = 15, nullable = false)
    private String status = Status.PENDING.toString();
    @Column(nullable = false)
    private boolean validated = false;
    @Column(nullable = false)
    private boolean approved = false;
    @Column(length = 255, nullable = false)
    private String photo;

    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private User user = new User();

    public FreeTransportationApplication(String photo) {
        this.dateSubmitted = new Date();
        this.status = Status.PENDING.toString();
        this.photo = photo;
    }

    public FreeTransportationApplication(String photo, User user) {
        this.dateSubmitted = new Date();
        this.status = "pending";
        this.photo = photo;
        this.user = user;
    }

    public FreeTransportationApplication() {

    }

    public Integer getId() {
        return id;
    }

    public Date getDateSubmitted() {
        return dateSubmitted;
    }

    public void setDateSubmitted(Date dateSubmitted) {
        this.dateSubmitted = dateSubmitted;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public boolean isValidated() {
        return validated;
    }

    public void setValidated(boolean validated) {
        this.validated = validated;
    }

    public boolean isApproved() {
        return approved;
    }

    public void setApproved(boolean approved) {
        this.approved = approved;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @Override
    public String toString() {
        return "FreeTransportationApplication{" +
                "id=" + id +
                ", dateSubmitted=" + dateSubmitted +
                ", status='" + status + '\'' +
                ", validated=" + validated +
                ", approved=" + approved +
                ", photo='" + photo + '\'' +
                ", user=" + user.toString() +
                '}';
    }
}