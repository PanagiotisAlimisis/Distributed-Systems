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
    private Long id;

    @Column(name = "FIRST_NAME", length = 46, nullable = false)
    private String firstName;

    @Column(name = "LAST_NAME", length = 46, nullable = false)
    private String lastName;

    @Column(length = 64, nullable = false, unique = true)
    private String email;

    @Column(length = 64, nullable = false)
    private String password;

    @Column(name = "UNEMPLOYMENT_START_DATE")
    private Date unemploymentStartDate;

    @ManyToOne
//    @JoinTable(name = "USERS_ROLES", joinColumns = @JoinColumn(name = "USER_ID"), inverseJoinColumns = @JoinColumn(name = "ROLE_ID"))
    private Role role = new Role();


    public User(String firstName, String lastName, String email, String password, Date unemploymentStartDate) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
        this.unemploymentStartDate = unemploymentStartDate;
    }

    public User() {

    }

    public Long getId() {
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

    public Date getUnemploymentStartDate() {
        return unemploymentStartDate;
    }

    public void setUnemploymentStartDate(Date unemploymentStartDate) {
        this.unemploymentStartDate = unemploymentStartDate;
    }

//    public Set<Role> getRoles() {
//        return roles;
//    }
//
//    public void setRoles(Set<Role> roles) {
//        this.roles = roles;
//    }
//
//    public void addRole(Role role) {
//        this.roles.add(role);
//    }

//    public Role getRole() {
//        return role;
//    }
//
    public void setRole(Role role) {
        this.role = role;
    }

//    public void setApplications(Set<Application> applications) {
//        this.applications = applications;
//    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", unemploymentStartDate=" + unemploymentStartDate +
                '}';
    }
}


