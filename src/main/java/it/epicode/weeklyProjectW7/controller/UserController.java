package it.epicode.weeklyProjectW7.controller;

import it.epicode.weeklyProjectW7.exception.BadRequestException;
import it.epicode.weeklyProjectW7.model.User;
import it.epicode.weeklyProjectW7.model.UserRequest;
import it.epicode.weeklyProjectW7.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class UserController {
    @Autowired
    private UserService userService;
    @GetMapping("/users")
    public List<User> getAll(){
        return userService.getAllUsers();
    }

    @GetMapping("/users/{username}")
    public User getUserByUsername(@PathVariable String username) {

        return userService.getUserByUsername(username);

    }

    @PutMapping("/users/{username}")
    public User updateUser(@PathVariable String username, @RequestBody @Validated UserRequest userRequest, BindingResult bindingResult) {

        if(bindingResult.hasErrors()){
            throw new BadRequestException(bindingResult.getAllErrors().toString());
        }

        return userService.updateUser(username, userRequest);

    }
    @PostMapping("/users")
    public User save(@RequestBody @Validated UserRequest userRequest, BindingResult bindingResult){
        if(bindingResult.hasErrors()){
            throw new BadRequestException(bindingResult.getAllErrors().toString());
        }

        return userService.save(userRequest);
    }
    @DeleteMapping("/users/{username}")
    public void deleteUser(@PathVariable String username) {

        userService.deleteUser(username);

    }
    @PatchMapping("/users/{username}")
    public User changeRole(@PathVariable String username, @RequestBody String role) {

        return userService.updateRoleUser(username, role);

    }
}
