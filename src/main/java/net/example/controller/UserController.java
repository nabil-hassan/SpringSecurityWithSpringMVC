package net.example.controller;

import net.example.dao.UserDAO;
import net.example.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.sql.SQLException;

/**
 * Handles login, logout, and user creation requests.
 */
@Controller
public class UserController {

    private static final Logger LOG = LoggerFactory.getLogger(UserController.class);
    private static final String CREATE_ERROR = "createUserErrorMsg";
    private static final String LOGIN_ERROR = "loginErrorMsg";

    @Autowired
    private UserDAO userDAO;

    @Autowired
    private UserService userService;

    /**
     * Displays the application login page.
     *
     * @return the login page path
     */
    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public String showLoginPage() {
        return "login";
    }

    @RequestMapping(value = "/public", method = RequestMethod.GET)
    public String showPublicPage() {
        return "public";
    }

    @RequestMapping(value = "/private", method = RequestMethod.GET)
    public String showPrivatePage() {
        return "private";
    }

    /**
     * Create the specified user. If an error occurs, display to the user, otherwise redirect to welcome page.
     */
    @RequestMapping(value = "/createUserForm/submit", method = RequestMethod.POST)
    public String doCreateUser(@RequestParam String username,
            @RequestParam String password,
            @RequestParam(required = false) Boolean rememberMe,
            RedirectAttributes redirectAttributes) throws SQLException {
        LOG.debug("Create user request received for user: {}", username);

        try {
            userService.createUser(username, password);
            userService.loginUser(username, password, rememberMe);
            return "redirect:/";
        } catch (Exception ex) {
            redirectAttributes.addFlashAttribute(CREATE_ERROR, ex.getMessage());
            redirectAttributes.addFlashAttribute("newUsrUsername", username);
            redirectAttributes.addFlashAttribute("newUsrPassword", password);
            return "redirect:/login";
        }
    }

}
