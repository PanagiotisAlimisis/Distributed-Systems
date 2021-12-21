package gr.hua.ds.freetransportation.entities;

import javax.persistence.*;
import java.util.Collection;
import java.util.Set;

@Entity
@Table(name = "ROLES")
public class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(length = 40, nullable = false, unique = true)
    private String role;

    @Column(length = 150, nullable = false)
    private String description;

    public Role() {
    }

    public Role(Integer id) {
        this.id = id;
    }

    public Role(String role, String description) {
        this.role = role;
        this.description = description;
    }

    public Integer getId() {
        return id;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return "Role{" +
                "id=" + id +
                ", role='" + role + '\'' +
                ", description='" + description + '\'' +
                '}';
    }
}
