package gr.hua.ds.freetransportation.util;

import gr.hua.ds.freetransportation.entities.User;
import gr.hua.ds.freetransportation.security.UserDet;
import org.springframework.security.core.context.SecurityContextHolder;

public class UserUtil {
    public static User getCurrentUser() {
        UserDet userDet = (UserDet) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return userDet.getUser();
    }
}
