package dev.filipe.loginapi.domain;

import dev.filipe.loginapi.entity.UserEntity;

public record CreateUserDTO(String name, String password) {

    public CreateUserDTO(UserEntity user){
        this(user.getName(), user.getEmail());
    }
}
