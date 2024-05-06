package io.github.karMiguel.library.controllers;

import io.github.karMiguel.library.mapper.UserMapper;
import io.github.karMiguel.library.services.UserDetailServices;
import io.github.karMiguel.library.services.UserServices;
import io.github.karMiguel.library.vo.AccountCredentialsVO;
import io.github.karMiguel.library.vo.UserVO;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@Tag(name = "User", description = "Endpoints for Managing User")
@RequestMapping("/api/user/v1")
@RestController
public class UserController {

    @Autowired
    private UserServices userServices;

    @PostMapping
    public ResponseEntity<AccountCredentialsVO> created(@RequestBody @Valid UserVO dto) {
        userServices.save(UserMapper.toUser(dto));
        return  ResponseEntity.status(HttpStatus.CREATED).build();
    }
}
