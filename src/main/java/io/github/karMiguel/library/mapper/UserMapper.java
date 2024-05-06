package io.github.karMiguel.library.mapper;

import io.github.karMiguel.library.model.User;
import io.github.karMiguel.library.vo.UserVO;
import org.modelmapper.ModelMapper;

public class UserMapper {

    private static ModelMapper mapper = new ModelMapper();

    public static User toUser(UserVO vo){
        return  mapper.map(vo, User.class);
    }

}
