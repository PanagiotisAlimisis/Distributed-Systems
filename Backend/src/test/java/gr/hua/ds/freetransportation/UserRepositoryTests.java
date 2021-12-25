package gr.hua.ds.freetransportation;

import gr.hua.ds.freetransportation.dao.UserRepository;
import gr.hua.ds.freetransportation.entities.Role;
import gr.hua.ds.freetransportation.entities.RoleTypes;
import gr.hua.ds.freetransportation.entities.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.annotation.Rollback;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Rollback(false)
public class UserRepositoryTests {

    @Autowired
    UserRepository repo;

    @Autowired
    private TestEntityManager entityManager;

    @Test
    public void testCreateUser() {
        Role r = entityManager.find(Role.class, 1);
        Role role = new Role(RoleTypes.OAED_EMPLOYEE.toInt());
        User u = new User("p", "a", "panagiotis", "");
        u.setRole(role);
        User s = repo.save(u);
        assertThat(s.getId()).isGreaterThan(0);
    }

    @Test
    public void fetchAllUsers() {
        List<User> users = (List<User>) repo.findAll();
        assertThat(users).isNotNull();
        for (User u : users) {
            System.out.println(u.toString());
        }
    }
}
