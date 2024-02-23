package it.epicode.weeklyProjectW7.service;

import it.epicode.weeklyProjectW7.exception.NotFoundException;
import it.epicode.weeklyProjectW7.model.Role;
import it.epicode.weeklyProjectW7.model.User;
import it.epicode.weeklyProjectW7.model.UserRequest;
import it.epicode.weeklyProjectW7.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PasswordEncoder encoder;

    public User save(UserRequest userRequest){

        User utente = new User();
        utente.setName(userRequest.getName());
        utente.setSurname(userRequest.getSurname());
        utente.setUsername(userRequest.getUsername());
        utente.setPassword(encoder.encode(userRequest.getPassword()));
        utente.setRole(Role.USER);

        return userRepository.save(utente);
    }

    public User getUserById(int id){
        return userRepository.findById(id).orElseThrow(()->new NotFoundException("User not found"));
    }

    public User getUserByUsername(String username){
        return userRepository.findByUsername(username).orElseThrow(()->new NotFoundException("Username not found"));
    }

    public List<User> getAllUsers(){
        return userRepository.findAll();
    }

    public User updateUser(String username, UserRequest userRequest){
        User utente =getUserByUsername(username);
        utente.setName(userRequest.getName());
        utente.setSurname(userRequest.getSurname());
        utente.setUsername(userRequest.getUsername());
        utente.setPassword(userRequest.getPassword());

        return userRepository.save(utente);
    }

    public User updateRoleUser(String username, String role){
        User utente =getUserByUsername(username);
        utente.setRole(Role.valueOf(role));
        return userRepository.save(utente);
    }

    public void deleteUser(String username){
        userRepository.deleteByUsername(username).orElseThrow(()->new NotFoundException("User not found"));
    }
}
