package gr.hua.ds.freetransportation.dao;

import gr.hua.ds.freetransportation.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
    @Query("SELECT u FROM User u WHERE u.email = :email")
    public User getUserByEmail(@Param("email") String email);

    public Long countById(Integer id);

    @Query("SELECT u FROM User u WHERE u.role.name = 'UNEMPLOYED'")
    public List<User> findAllUnemployed();

    @Query("SELECT u FROM User u WHERE u.role.name = 'OAED_EMPLOYEE' OR u.role.name = 'TRANSPORTATION_EMPLOYEE'")
    public List<User> findAllEmployed();
}
