package gr.hua.ds.freetransportation.entities;

import javax.persistence.*;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "USERS")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "FIRST_NAME", length = 46, nullable = false)
    private String firstName;

    @Column(name = "LAST_NAME", length = 46, nullable = false)
    private String lastName;

    @Column(length = 64, nullable = false, unique = true)
    private String email;

    @Column(length = 64, nullable = false)
    private String password;

    @Column(name = "ENABLED", nullable = false)
    private boolean enabled;

    @ManyToOne
    private Role role = new Role();

    @OneToMany
    @JoinTable(name = "USERS_FREE_TRANSPORTATION_APPLICATIONS", joinColumns = @JoinColumn(name = "USER_ID"), inverseJoinColumns = @JoinColumn(name = "APPLICATION_ID"))
    private Set<FreeTransportationApplication> freeTransportationApplications = new HashSet<>();

    @OneToMany
    @JoinTable(name = "USERS_UNEMPLOYMENT_APPLICATIONS", joinColumns = @JoinColumn(name = "USER_ID"), inverseJoinColumns = @JoinColumn(name = "APPLICATION_ID"))
    private Set<UnemploymentApplication> unemploymentApplications = new HashSet<>();


    public User(String firstName, String lastName, String email, String password) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
        this.enabled = true;
        this.role = new Role(RoleTypes.DEFAULT_USER.toInt());
    }

    public User() {

    }

    public int getId() {
        return id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }


    public Set<FreeTransportationApplication> getFreeTransportationApplications() {
        return freeTransportationApplications;
    }

    public void setFreeTransportationApplications(Set<FreeTransportationApplication> freeTransportationApplications) {
        this.freeTransportationApplications = freeTransportationApplications;
    }

    public void addFreeTransportationApplication(FreeTransportationApplication application) {
        this.freeTransportationApplications.add(application);
    }

    public Set<UnemploymentApplication> getUnemploymentApplications() {
        return unemploymentApplications;
    }

    public void setUnemploymentApplications(Set<UnemploymentApplication> unemploymentApplications) {
        this.unemploymentApplications = unemploymentApplications;
    }

    public void addUnemploymentApplication(UnemploymentApplication application) {
        this.unemploymentApplications.add(application);
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                '}';
    }

}


