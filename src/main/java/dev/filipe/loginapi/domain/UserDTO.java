package dev.filipe.loginapi.domain;

import dev.filipe.loginapi.entity.UserEntity;


public record UserDTO(String name, String email) {

    public UserDTO(UserEntity user){
        this(user.getName(), user.getEmail());
    }
}
