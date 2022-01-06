package gr.hua.ds.freetransportation.dao;

import gr.hua.ds.freetransportation.entities.UnemploymentApplication;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UnemploymentApplicationRepository extends JpaRepository<UnemploymentApplication, Integer> {

    @Query("SELECT COUNT(a) FROM UnemploymentApplication a WHERE a.user.id = :user_id AND (a.status = 'PENDING' OR a.status = 'ACCEPT')")
    public int countPendingApplications(@Param("user_id") Integer userId);

    @Query("SELECT a FROM UnemploymentApplication a WHERE a.status = 'PENDING'")
    public Page<UnemploymentApplication> findPendingApplications(Pageable pageable);

}
