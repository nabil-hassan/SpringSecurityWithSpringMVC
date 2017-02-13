package net.example.config.context;

import net.example.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@Import(PersistenceContext.class)
public class ServiceContext {

    @Autowired
    private PersistenceContext persistenceContext;

    @Bean
    public UserService userService() {
        return  new UserService(persistenceContext.userDAO());
    }


}
