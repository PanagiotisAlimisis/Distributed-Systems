package gr.hua.ds.freetransportation.dao;

import gr.hua.ds.freetransportation.entities.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository extends JpaRepository<Role, Integer> {
}
