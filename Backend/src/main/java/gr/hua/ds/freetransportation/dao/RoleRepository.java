package gr.hua.ds.freetransportation.dao;

import gr.hua.ds.freetransportation.entities.Role;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository extends CrudRepository<Role, Integer> {
}
