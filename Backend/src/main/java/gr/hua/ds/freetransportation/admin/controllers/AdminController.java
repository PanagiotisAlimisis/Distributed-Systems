package gr.hua.ds.freetransportation.admin.controllers;

import gr.hua.ds.freetransportation.entities.Role;
import gr.hua.ds.freetransportation.entities.User;
import gr.hua.ds.freetransportation.exceptions.UserNotFoundException;
import gr.hua.ds.freetransportation.export.UserCsvExporter;
import gr.hua.ds.freetransportation.admin.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.security.Principal;
import java.util.List;

@Controller
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    private UserService service;

    @GetMapping("/users")
    public String viewCreateNewUserPage(Model model) {
        addRolesToModel(model);
        User user = new User();
        model.addAttribute("user", user);
        return "user_form";
    }

    @PostMapping("/users")
    public String createNewUser(@ModelAttribute("user") User user, BindingResult bindingResult, Model model) {
        if (user.getId() == null && !service.isEmailUnique(user.getId(), user.getEmail())) {
            FieldError error = new FieldError("email", "email", "Email must be unique");
            bindingResult.addError(error);
            addRolesToModel(model);
            return "user_form";
        }
        service.saveUser(user);

        return "redirect:/admin/home";
    }

    @GetMapping("/users/edit/{id}")
    public String editUser(@PathVariable(name = "id") Integer id, RedirectAttributes redirectAttributes, Model model) {
        try {
            User user = service.get(id);
            List<Role> roleList = service.listRoles();
            model.addAttribute("user", user);
            model.addAttribute("roleList", roleList);
            return "user_form";
        } catch (UserNotFoundException e) {
            redirectAttributes.addFlashAttribute("message", e.getMessage());
            return "redirect:/admin/home";
        }
    }

    @GetMapping("/users/delete/{id}")
    public String deleteUser(@PathVariable(name = "id") Integer id, RedirectAttributes redirectAttributes, Model model) {
        try {
            service.delete(id);
            redirectAttributes.addFlashAttribute("message", "The user ID " + id + " has been delete successfully");
        } catch (UserNotFoundException e) {
            redirectAttributes.addFlashAttribute("message", e.getMessage());
        }
        return "redirect:/admin/home";
    }

    @GetMapping("/users/export/csv")
    public void exportToCSV(HttpServletResponse response) throws IOException {
        List<User> listUsers = service.listAll();
        UserCsvExporter exporter = new UserCsvExporter();
        exporter.export(listUsers, response);
    }

    @GetMapping("/forbidden")
    public String view403Page(Principal user, Model model) {
        if (user != null) {
            String firstName = service.getNameByEmail(user.getName());
            model.addAttribute("msg", "Hi " + firstName
                    + ", you do not have permission to access this page!");
        } else {
            model.addAttribute("msg",
                    "You do not have permission to access this page!");
        }

        return "forbidden";
    }

    @GetMapping("/home")
    public String listFirstPage(Model model) {
        return listByPage(1, model, "id", "asc", null);
    }

    @GetMapping("/users/page/{pageNumber}")
    public String listByPage(@PathVariable(name = "pageNumber") int pageNumber, Model model,
                             @Param("sortField") String sortField, @Param("sortDir") String sortDir,
                             @Param("keyword") String keyword) {
        Page<User> page = service.listByPage(pageNumber, sortField, sortDir, keyword);
        List<User> userList = page.getContent();

        long startCount = (pageNumber - 1) * UserService.USERS_PER_PAGE + 1;
        long endCount = startCount + UserService.USERS_PER_PAGE - 1;
        if (endCount > page.getTotalElements()) {
            endCount = page.getTotalElements();
        }

        String reverseSortDir = sortDir.equals("asc") ? "desc" : "asc";

        model.addAttribute("totalPages", page.getTotalPages());
        model.addAttribute("userList", userList);
        model.addAttribute("totalItems", page.getTotalElements());
        model.addAttribute("startCount", startCount);
        model.addAttribute("endCount", endCount);
        model.addAttribute("currentPage", pageNumber);
        model.addAttribute("sortField", sortField);
        model.addAttribute("sortDir", sortDir);
        model.addAttribute("reverseSortDir", reverseSortDir);
        model.addAttribute("keyword", keyword);

        return "index";
    }

    private void addRolesToModel(Model model) {
        List<Role> roleList = service.getAllRoles();
        roleList.removeIf(role -> role.getName().equals("DEFAULT_USER") || role.getName().equals("UNEMPLOYED"));
        model.addAttribute("roleList", roleList);
    }

}
