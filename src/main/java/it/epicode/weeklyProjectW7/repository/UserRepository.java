package it.epicode.weeklyProjectW7.repository;

import it.epicode.weeklyProjectW7.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Integer> {

    public Optional<User> findByUsername    (String username);

    public Optional<User> deleteByUsername  (String username);
}
