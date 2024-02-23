package it.epicode.weeklyProjectW7.controller;

import it.epicode.weeklyProjectW7.exception.BadRequestException;
import it.epicode.weeklyProjectW7.exception.LoginFaultException;
import it.epicode.weeklyProjectW7.model.LoginRequest;
import it.epicode.weeklyProjectW7.model.User;
import it.epicode.weeklyProjectW7.model.UserRequest;
import it.epicode.weeklyProjectW7.security.JwtTools;
import it.epicode.weeklyProjectW7.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AuthController {
    @Autowired
    private UserService userService;
    @Autowired
    private JwtTools jwtTools;
    @Autowired
    private PasswordEncoder encoder;
    @PostMapping("/auth/register")
    public User register(@RequestBody @Validated UserRequest userRequest, BindingResult bindingResult){
        if(bindingResult.hasErrors()){
            throw new BadRequestException(bindingResult.getAllErrors().toString());
        }

        return userService.save(userRequest);
    }

    @PostMapping("/auth/login")
    public String login(@RequestBody @Validated LoginRequest loginRequest, BindingResult bindingResult){
        if(bindingResult.hasErrors()){
            throw new BadRequestException(bindingResult.getAllErrors().toString());
        }

        User user = userService.getUserByUsername(loginRequest.getUsername());

        if(encoder.matches(loginRequest.getPassword(), user.getPassword())){
            return jwtTools.createToken(user);
        }
        else{
            throw new LoginFaultException("Username or password are incorrect");
        }

    }
}
