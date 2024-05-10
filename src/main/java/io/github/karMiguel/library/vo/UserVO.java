package io.github.karMiguel.library.vo;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserVO {

    @NotBlank(message = "O nome de usuário não pode estar em branco")
    private String userName;

    @NotBlank(message = "O nome completo não pode estar em branco")
    private String fullName;

    @NotBlank(message = "A senha não pode estar em branco")
    @Size(min = 6, message = "A senha deve ter pelo menos 6 caracteres")
    private String password;

}
