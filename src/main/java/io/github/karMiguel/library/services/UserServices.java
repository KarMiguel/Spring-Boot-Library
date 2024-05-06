package io.github.karMiguel.library.services;

import io.github.karMiguel.library.exceptions.UsernameUniqueViolationException;
import io.github.karMiguel.library.model.Role;
import io.github.karMiguel.library.model.User;
import io.github.karMiguel.library.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserServices {

    private final UserRepository repository;

    private final PasswordEncoder passwordEncoder;

    public void save(User user) throws UsernameUniqueViolationException {

        try {

            user.setEnabled(true);
            user.setAccountNonExpired(true);
            user.setAccountNonLocked(true);
            user.setCredentialsNonExpired(true);
            user.setPassword(passwordEncoder.encode(user.getPassword()));
            repository.save(user);

        }catch (DataIntegrityViolationException ex){
            throw new UsernameUniqueViolationException(String.format("Username '%s' ja Cadastrado!",user.getUsername()));
        }

    }


}
