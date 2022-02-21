package gr.hua.ds.freetransportation.dao;

import gr.hua.ds.freetransportation.entities.FreeTransportationApplication;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FreeTransportationApplicationRepository extends JpaRepository<FreeTransportationApplication, Integer> {
    @Query("SELECT COUNT(a) FROM FreeTransportationApplication a WHERE a.user.id = :user_id AND (a.status = 'PENDING' OR a.status = 'ACCEPT')")
    public int countPendingApplications(@Param("user_id") Integer userId);

    @Query("SELECT a FROM FreeTransportationApplication a WHERE a.status = 'PENDING' AND a.validated = false")
    public Page<FreeTransportationApplication> findUnansweredApplications(Pageable pageable);

    @Query("SELECT a FROM FreeTransportationApplication a WHERE a.status = 'PENDING' AND a.validated = true")
    public Page<FreeTransportationApplication> findUnansweredAndValidatedApplications(Pageable pageable);

    @Query("SELECT a.id FROM FreeTransportationApplication a WHERE a.user.id = :userId")
    public List<FreeTransportationApplication> selectByUserId(Integer userId);



}
