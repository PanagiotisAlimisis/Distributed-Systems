package gr.hua.ds.freetransportation.entities;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "APPLICATIONS")
public class Application {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(name = "date_submitted")
    private Date dateSubmitted;
    @Column(length = 10)
    private String status;
    private boolean validated;
    private boolean approved;
    @Column(name = "unemployment_start_date")
    private Date unemploymentStartDate;
    private String photo;

    private Long userId;

    public Application(Date dateSubmitted, String status, boolean validated, boolean approved, Date unemploymentStartDate, String photo) {
        this.dateSubmitted = dateSubmitted;
        this.status = status;
        this.validated = validated;
        this.approved = approved;
        this.unemploymentStartDate = unemploymentStartDate;
        this.photo = photo;
    }

    public Application() {

    }

    public int getId() {
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

    public Date getUnemploymentStartDate() {
        return unemploymentStartDate;
    }

    public void setUnemploymentStartDate(Date unemploymentStartDate) {
        this.unemploymentStartDate = unemploymentStartDate;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    @Override
    public String toString() {
        return "Application{" +
                "id=" + id +
                ", dateSubmitted=" + dateSubmitted +
                ", status=" + status +
                ", validated=" + validated +
                ", approved=" + approved +
                ", unemploymentStartDate=" + unemploymentStartDate +
                ", photo='" + photo + '\'' +
                '}';
    }
}