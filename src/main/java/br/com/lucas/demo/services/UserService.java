package br.com.lucas.demo.services;

import br.com.lucas.demo.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.yaml.snakeyaml.internal.Logger;


@Service

public class UserService implements UserDetailsService {

    private final Logger logger = Logger.getLogger(UserService.class.getName());

    @Autowired()
    UserRepository repository;

    public UserService(UserRepository repository) {
        this.repository = repository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        logger.warn("find one user by name "+ username);
        var user = repository.findByUserName(username);
        if(user != null){
            return user;
        } else {
            throw new UsernameNotFoundException("UserName" + username + "not found");
        }

    }
}
