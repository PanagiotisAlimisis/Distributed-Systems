package gr.hua.ds.freetransportation;

import gr.hua.ds.freetransportation.dao.RoleRepository;
import gr.hua.ds.freetransportation.entities.Role;
import gr.hua.ds.freetransportation.entities.RoleTypes;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.Rollback;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Rollback(false)
public class RoleRepositoryTests {

    @Autowired
    RoleRepository repo;

    @Test
    public void testCreateFirstRole() {
        Role role = new Role(RoleTypes.UNEMPLOYED.toString(), "");
        Role r = repo.save(role);
        assertThat(r.getId()).isGreaterThan(0);
    }

    @Test
    public void testCreateRestRoles() {
        Role role1 = new Role(RoleTypes.OAED_EMPLOYEE.toString(), "");
        Role role2 = new Role(RoleTypes.TRANSPORTATION_EMPLOYEE.toString(), "");
        Role role3 = new Role(RoleTypes.ADMIN.toString(), "");
        repo.saveAll(List.of(role1, role2, role3));
    }
}
