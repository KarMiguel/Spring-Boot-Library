package io.github.karMiguel.library.services;


import io.github.karMiguel.library.exceptions.UsernameUniqueViolationException;
import io.github.karMiguel.library.model.Role;
import io.github.karMiguel.library.model.User;
import io.github.karMiguel.library.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.logging.Logger;



@Service
public class UserDetailServices implements UserDetailsService {

    private Logger logger = Logger.getLogger(UserDetailServices.class.getName());

    @Autowired
    private  UserRepository repository;


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        logger.info("Finding one user by name "+username+"!");
        var user = repository.findByUsername(username);
        if (user != null){
            return  user;
        }else{
            throw new UsernameNotFoundException("Username "+username+" not found!");
        }
    }
}
