package net.example.dao;

import net.example.domain.ExtendedUserDetails;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import javax.sql.DataSource;
import java.sql.*;

/**
 * Provides access to the application's datasource - specifically, data related to the system's users, and their roles
 * and permissions.
 */
public class UserDAO {

    private static final Logger LOG = LoggerFactory.getLogger(UserDAO.class);
    
    //TODO: merge queries
    private static final String GET_USER_DETAILS_QUERY
            = "SELECT u.id, u.username, u.password, u.enabled, u.email FROM USERS u WHERE u.username = ?";
    private static final String GET_AUTHORITIES_QUERY
            = "SELECT r.name FROM ROLES r INNER JOIN user_roles ur ON ur.role_id = r.id WHERE ur.user_id = ?";

    private DataSource dataSource;

    public UserDAO(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    /**
     * Looks up the user by username. If more than one record exists, or no records exist, an exception will be thrown.
     */
    public ExtendedUserDetails findUserByUsername(String username) {
        LOG.debug("Find user : '{}'", username);

        Connection connection = null;
        ExtendedUserDetails userDetails = new ExtendedUserDetails();
        int resultCount = 0;

        // Lookup the basic user details - username, password etc
        try {
            connection = dataSource.getConnection();

            PreparedStatement query = connection.prepareStatement(GET_USER_DETAILS_QUERY);
            query.setString(1, username);

            ResultSet rs = query.executeQuery();

            while (rs.next()) {
                if (++resultCount > 1) {
                    break;
                }
                userDetails.setId(rs.getLong("id"));
                userDetails.setUsername(rs.getString("username"));
                userDetails.setPassword(rs.getString("password"));
                userDetails.setEmail(rs.getString("email"));
                userDetails.setEnabled(rs.getBoolean("enabled"));
            }
            rs.close();
        } catch(SQLException ex) {
            throw new RuntimeException("Exception encountered performing createUser() query", ex);
        } finally {
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException ex) {
                    LOG.warn("Unable to close database connection", ex);
                }
            }
        }

        if (resultCount != 1) {
            throw new RuntimeException("Invalid result for findUserByUsername(). Expected 1 result, but got "
                    + resultCount);
        }

        // Lookup authorities
        try {
            connection = dataSource.getConnection();

            PreparedStatement query = connection.prepareStatement(GET_AUTHORITIES_QUERY);
            query.setLong(1, userDetails.getId());

            ResultSet rs = query.executeQuery();

            while (rs.next()) {
                userDetails.addAuthority(new SimpleGrantedAuthority(rs.getString("name")));
            }

            rs.close();
        } catch(SQLException ex) {
            throw new RuntimeException("Exception encountered performing createUser() query", ex);
        } finally {
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException ex) {
                    LOG.warn("Unable to close database connection", ex);
                }
            }
        }

        return userDetails;
    }

}
