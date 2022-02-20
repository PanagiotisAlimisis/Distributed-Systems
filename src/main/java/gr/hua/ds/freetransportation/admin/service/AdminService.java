package gr.hua.ds.freetransportation.admin.service;

import gr.hua.ds.freetransportation.dao.RoleRepository;
import gr.hua.ds.freetransportation.dao.UserRepository;
import gr.hua.ds.freetransportation.entities.Role;
import gr.hua.ds.freetransportation.RoleTypes;
import gr.hua.ds.freetransportation.entities.User;
import gr.hua.ds.freetransportation.exceptions.UserNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class AdminService {
    public static final int USERS_PER_PAGE = 10;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;


    public List<User> listAll() {
        return (List<User>) userRepository.findAll();
    }


    /**
     * Saves a user object. If it is new the default role is DEFAULT_USER.
     * @param user
     * @return
     */
    public User saveUser(User user) {
        boolean isUpdatingUser = (user.getId() != null);
        if (!isUpdatingUser) {
            encodePassword(user);
        }
        if (user.getRole().getId() == null) {
            Role role = roleRepository.findByRoleName(RoleTypes.DEFAULT_USER.toString());
            user.setRole(role);
        }
        return userRepository.save(user);
    }

    public List<Role> getAllRoles() {
        return roleRepository.findAll();
    }

    public boolean isEmailUnique(Integer id, String email) {
        User user = userRepository.getUserByEmail(email);
        if (user == null) {
            return true;
        }
        boolean isCreatingNew = (id == null);
        if (isCreatingNew) {
            return false;
        } else {
            if (!user.getId().equals(id)) {
                return false;
            }
        }
        return true;
    }

    /**
     * Returns one user based on their id.
     * @param id
     * @return User
     * @throws UserNotFoundException
     */
    public User get(Integer id) throws UserNotFoundException {
        try {
            return userRepository.findById(id).get();
        } catch (NoSuchElementException e) {
            throw new UserNotFoundException("Could not find any user with ID " + id);
        }
    }

    /**
     * Returns a list of all available roles.
     * @return List<Role>
     */
    public List<Role> listRoles() {
        return (List<Role>) roleRepository.findAll();
    }

    /**
     * Deletes the user with the specified id.
     * @param id
     * @throws UserNotFoundException
     */
    public void delete(Integer id) throws UserNotFoundException {
        Long count = userRepository.countById(id);
        if (count == null || count == 0) {
            throw new UserNotFoundException("Could not find any user with ID " + id);
        }
        userRepository.deleteById(id);
    }

    public String getNameByEmail(String email) {
        return userRepository.getUserByEmail(email).getFirstName();
    }

    public Page<User> listByPage(int pageNumber, String sortField, String sortDir) {
        Sort sort = Sort.by(sortField);
        sort = sortDir.equals("asc") ? sort.ascending() : sort.descending();
        Pageable pageable = PageRequest.of(pageNumber - 1, USERS_PER_PAGE, sort);
        return userRepository.findAll(pageable);
    }

    private void encodePassword(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
    }

    public List<User> listAllUnemployed() {
        return userRepository.findAllUnemployed();
    }

    public List<User> listAllEmployed() {
        return userRepository.findAllEmployed();
    }
}
