package gr.hua.ds.freetransportation.security;

import gr.hua.ds.freetransportation.entities.Role;
import gr.hua.ds.freetransportation.entities.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Collections;

public class UserDet implements UserDetails {
    private User user;

    public UserDet(User user) {
        this.user = user;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        Role role = user.getRole();
        SimpleGrantedAuthority authority = new SimpleGrantedAuthority(role.getName());
        return Collections.singleton(authority);
    }

    @Override
    public String getPassword() {
        return user.getPassword();
    }

    /**
     *
     * @return Email of the user.
     */
    @Override
    public String getUsername() {
        return user.getEmail();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return user.isEnabled();
    }

    public String getFirstName() {
        return user.getFirstName();
    }

    public String getLastName() {
        return user.getLastName();
    }

    public User getUser() {
        return user;
    }

}
