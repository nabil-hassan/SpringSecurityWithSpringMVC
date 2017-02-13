package net.example.service;

import net.example.dao.UserDAO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class UserService {
    
    private static final Logger LOG = LoggerFactory.getLogger(UserService.class);
    private UserDAO userDAO;

    public UserService(UserDAO userDAO) {
        this.userDAO = userDAO;
    }

    /**
     * Confirms whether the user is authenticated.
     */
    public boolean userIsAuthenticated() {
        return false;
    }

    /**
     * Attempts to login user; throws an Exception if unable to.
     */
    public void loginUser(String username, String password, Boolean rememberUser) throws Exception {

    }

    /**
     * Creates a new user account with the specified credentials.
     */
    public void createUser(String username, String password) throws Exception {

    }

}
