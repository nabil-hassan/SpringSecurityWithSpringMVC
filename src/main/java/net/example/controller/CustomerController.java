package net.example.controller;

import net.example.config.context.SecurityContext;
import net.example.dao.UserDAO;
import net.example.domain.ExtendedUserDetails;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class CustomerController {

    private static final Logger LOG = LoggerFactory.getLogger(CustomerController.class);

    @Autowired
    UserDAO userDAO;

    @RequestMapping(method = RequestMethod.GET, path = "/customerList")
    public String getCustomerList() {
        LOG.info("getCustomerList()");

        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        if (principal instanceof ExtendedUserDetails) {
            ExtendedUserDetails userDetails = ((ExtendedUserDetails) principal);
            LOG.info("User is: {}", userDetails);
        } else {
            LOG.info("User: {}", principal.toString());
        }

        return "customerList";
    }

}
