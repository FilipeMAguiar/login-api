package dev.filipe.loginapi.controller;

import dev.filipe.loginapi.domain.CreateUserDTO;
import dev.filipe.loginapi.domain.UpdateUserDTO;
import dev.filipe.loginapi.domain.UserDTO;
import dev.filipe.loginapi.entity.UserEntity;
import dev.filipe.loginapi.exception.UserAlreadyExistsException;
import dev.filipe.loginapi.repository.UserRepository;
import dev.filipe.loginapi.service.UserService;
import io.swagger.annotations.ApiOperation;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;
import java.util.stream.Collectors;

@AllArgsConstructor
@RestController
@RequestMapping("/users")
public class UserController {

    private UserRepository repository;

    private UserService service;

    @ApiOperation("This method is responsible for get all users from database")
    @GetMapping()
    public ResponseEntity<List<UserDTO>> getUsers() {
        List<UserDTO> response = repository.findAll().stream().map(UserDTO::new).collect(Collectors.toList());
        return ResponseEntity.ok(response);
    }

    @ApiOperation("This method is responsible for create users in database")
    @PostMapping()
    @Transactional
    public ResponseEntity<CreateUserDTO> createUser(@RequestBody @Valid UserEntity request, UriComponentsBuilder uriBuilder) throws UserAlreadyExistsException {
        try {
            UserEntity user = service.createUser(request);
            var uri = uriBuilder.path("/users/{id}").buildAndExpand(user.getId()).toUri();
            return ResponseEntity.created(uri).body(new CreateUserDTO(user));
        } catch (UserAlreadyExistsException e) {
            throw new UserAlreadyExistsException("Was not possible create this user because the e-mail already exists on our database.");
        }
    }



    @ApiOperation("This method is responsible for updating users in database")
    @PutMapping
    @Transactional
    public ResponseEntity<UserDTO> updateUser(@RequestBody UpdateUserDTO request){
        if(repository.existsById(request.id())) {
            var user = repository.getReferenceById(request.id());
            user.updateInfo(request);
            return ResponseEntity.ok(new UserDTO(user));
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @ApiOperation("This method is responsible for delete user of database")
    @DeleteMapping("/{id}")
    @Transactional
    public ResponseEntity<Void> deleteUser(@PathVariable Long id){
        if(repository.existsById(id)) {
            repository.deleteById(id);
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }

}
