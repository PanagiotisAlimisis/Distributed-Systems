package gr.hua.ds.freetransportation.dao;

import gr.hua.ds.freetransportation.entities.FreeTransportationApplication;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FreeTransportationApplicationRepository extends CrudRepository<FreeTransportationApplication, Long> {
}
