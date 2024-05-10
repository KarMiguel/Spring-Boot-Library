package io.github.karMiguel.library.repository;

import io.github.karMiguel.library.model.ResetPassword;
import io.github.karMiguel.library.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ResetPasswordRepository extends JpaRepository<ResetPassword,Long> {

    ResetPassword findTopByUserOrderByDateCreatedDesc(User user);

}
