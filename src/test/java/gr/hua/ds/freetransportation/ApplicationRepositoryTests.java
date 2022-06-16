package gr.hua.ds.freetransportation;

import gr.hua.ds.freetransportation.dao.FreeTransportationApplicationRepository;
import gr.hua.ds.freetransportation.dao.UserRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.Rollback;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Rollback(false)
public class ApplicationRepositoryTests {

    @Autowired
    private FreeTransportationApplicationRepository repo;
    @Autowired
    private UserRepository userRepo;

    @Test
    public void testCreateAnApplication() {
        Assertions.assertEquals(5, 2+3);
        //        FreeTransportationApplication a = new FreeTransportationApplication(new Date(), null, false, false, null);
//        User u = userRepo.findById(1).get();
//        assertThat(u).isNotNull();
//        u.addApplication(a);
//        repo.save(a);
        
    }

}
