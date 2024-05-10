package io.github.karMiguel.library.services;

import io.github.karMiguel.library.exceptions.PasswordInvalidException;
import io.github.karMiguel.library.exceptions.UsernameUniqueViolationException;
import io.github.karMiguel.library.model.Role;
import io.github.karMiguel.library.model.User;
import io.github.karMiguel.library.repository.UserRepository;
import io.github.karMiguel.library.vo.UpdatePasswordVO;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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


    public User updatePassword(UpdatePasswordVO dto) {
        User user = repository.findByUserName(dto.getUsername())
                .orElseThrow(() -> new EntityNotFoundException(
                        String.format("Usuário '%s' não encontrado", dto.getUsername())));

        if (dto.getNewPassword().equals(dto.getConfPassword())) {
            user.setPassword(passwordEncoder.encode(dto.getConfPassword()));
            return repository.save(user);
        } else {
            throw new PasswordInvalidException("As senhas não coincidem");
        }
    }
    public User findByUsername(String username) {
        return repository.findByUserName(username).orElseThrow(
                ()-> new EntityNotFoundException(String.format("Usuário com username = '%s' não encontrado",username))
        );
    }

}
