package io.github.karMiguel.library.services;


import io.github.karMiguel.library.model.ResetPassword;
import io.github.karMiguel.library.model.StatusResetPassword;
import io.github.karMiguel.library.model.User;
import io.github.karMiguel.library.repository.ResetPasswordRepository;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class ResetPasswordService {

    @Autowired
    private ResetPasswordRepository resetPasswordRepository;

    public void saveResetCode(User user, String code) {
        ResetPassword resetPassword = new ResetPassword();
        resetPassword.setUser(user);
        resetPassword.setStatus(StatusResetPassword.SEND);
        resetPassword.setCode(code);
        resetPasswordRepository.save(resetPassword);
    }

    public ResetPassword getLatestResetCode(User user) {
        return resetPasswordRepository.findTopByUserOrderByDateCreatedDesc(user);
    }

    public void markCodeAsUsed(ResetPassword resetPassword,String newPassword) {
        resetPassword.setStatus(StatusResetPassword.DONE);
        resetPassword.setNewPassword(newPassword);
        resetPasswordRepository.save(resetPassword);
    }
}
