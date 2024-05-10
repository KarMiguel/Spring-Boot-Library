package io.github.karMiguel.library.controllers;

import io.github.karMiguel.library.exceptions.ResponseSuccess;
import io.github.karMiguel.library.mapper.UserMapper;
import io.github.karMiguel.library.model.ResetPassword;
import io.github.karMiguel.library.model.StatusResetPassword;
import io.github.karMiguel.library.model.User;
import io.github.karMiguel.library.services.EmailService;
import io.github.karMiguel.library.services.ResetPasswordService;
import io.github.karMiguel.library.services.UserServices;
import io.github.karMiguel.library.utils.ResetPasswordUtil;
import io.github.karMiguel.library.vo.AccountCredentialsVO;
import io.github.karMiguel.library.vo.UpdatePasswordVO;
import io.github.karMiguel.library.vo.UserVO;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.mail.MessagingException;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@Tag(name = "User", description = "Endpoints for Managing User")
@RequestMapping("/api/user/v1")
@RestController
public class UserController {

    @Autowired
    private UserServices userServices;
    @Autowired

    private EmailService emailService;
    @Autowired

    private ResetPasswordService resetPasswordService;


    @PostMapping
    public ResponseEntity<AccountCredentialsVO> created(@RequestBody @Valid UserVO dto) {
        userServices.save(UserMapper.toUser(dto));
        return  ResponseEntity.status(HttpStatus.CREATED).build();
    }


    @PostMapping("/reset-password")
    public ResponseEntity<?> resetPassword(@RequestParam String email) throws MessagingException {
       try {
            User user = userServices.findByUsername(email);

            ResetPassword latestResetCode = resetPasswordService.getLatestResetCode(user);
            if (latestResetCode != null && latestResetCode.getStatus() == StatusResetPassword.SEND) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ResponseSuccess("Um código de redefinição já foi enviado ao seu e-mail."));
            }

            String code = ResetPasswordUtil.generateCode();
            emailService.enviarPedidoRedefinicaoSenha(email, code);

            resetPasswordService.saveResetCode(user, code);

            return ResponseEntity.ok(new ResponseSuccess("Código de confirmação enviado por e-mail."));
       } catch (Exception e) {
           return ResponseEntity.ok(new ResponseSuccess("Email invalido no nosso banco de dados."));
       }
    }


    @PostMapping("/reset-password/valid")
    public ResponseEntity<?> validateResetPassword(@RequestBody @Valid UpdatePasswordVO dto) {
        try {
            if (!ResetPasswordUtil.validateCode(dto.getCode())) {
                return ResponseEntity.badRequest().body(new ResponseSuccess("Código de redefinição inválido."));
            }
            User user = userServices.findByUsername(dto.getUsername());

            ResetPassword latestResetCode = resetPasswordService.getLatestResetCode(user);
            if (latestResetCode == null || !latestResetCode.getCode().equals(dto.getCode())) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ResponseSuccess("Código de redefinição fornecido é inválido ou expirado."));
            }
            User newPassword =   userServices.updatePassword(dto);
            resetPasswordService.markCodeAsUsed(latestResetCode,newPassword.getPassword().toString());

            return ResponseEntity.ok(new ResponseSuccess("Senha redefinida com sucesso!"));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erro ao processar a solicitação.");
        }
    }
}
