package gr.hua.ds.freetransportation.entities;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "UNEMPLOYMENT_APPLICATIONS")
public class UnemploymentApplication {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "DATE_SUBMITTED", nullable = false)
    private Date dateSubmitted;
    @Column(length = 500, nullable = true)
    private String reason;
    @Column(length = 255, nullable = true)
    private String photo;
    @Column(length = 15, nullable = false)
    private String status;

    public UnemploymentApplication() {
    }

    public UnemploymentApplication(String reason, String photo) {
        this.dateSubmitted = new Date();
        this.reason = reason;
        this.photo = photo;
        this.status = "pending";
    }
}
