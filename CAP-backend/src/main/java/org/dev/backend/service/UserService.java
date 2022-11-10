package org.dev.backend.service;

import lombok.extern.slf4j.Slf4j;
import org.dev.backend.model.User;
import org.dev.backend.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service
public class UserService {
    private final UserRepository userRepository;


    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public void saveUser(User user) {
        log.info("Saving user...");
        userRepository.save(user);
    }

    public void deleteUser(int id) {
        log.info("Deleting user...");
        userRepository.delete(id);
    }

    public User findByLogin(String login) throws Exception {
        log.info("Finding user...");
        Optional<User> user = userRepository.findByLogin(login);
        return user.orElseThrow(() -> new Exception("No user found with this login!"));
    }

    public Boolean findAny() {
        log.info("finding all users");
        List<User> users = userRepository.findAll();
        return !users.isEmpty();
    }
}
