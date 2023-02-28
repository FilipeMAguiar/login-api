package dev.filipe.loginapi.service;

import dev.filipe.loginapi.entity.UserEntity;
import dev.filipe.loginapi.exception.UserAlreadyExistsException;
import dev.filipe.loginapi.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
public class UserService {

    private UserRepository repository;

    public UserEntity createUser(UserEntity request) throws UserAlreadyExistsException {
        if (userExists(request)) {
            throw new UserAlreadyExistsException();
        }
        UserEntity user = new UserEntity(request);
        repository.save(user);
        return user;
    }

    private Boolean userExists(UserEntity request) {
        Optional<UserEntity> existingUser = repository.findByEmail(request.getEmail());
        return existingUser.isPresent();
    }
}
