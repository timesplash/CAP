package org.dev.backend.CAP.service;

import lombok.extern.slf4j.Slf4j;
import org.dev.backend.CAP.repository.UserRepository;
import org.dev.backend.CAP.model.User;
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

    public void deleteUser(String login) {
        userRepository.delete(login);
    }

    public User findByLogin(String login) throws Exception {
        log.info("Finding user..." + login);
        Optional<User> user = userRepository.findByLogin(login);
        return user.orElseThrow(() -> new Exception("No user found with this login!"));
    }

    public Boolean findAny() {
        log.info("finding all users");
        List<User> users = userRepository.findAll();
        return users.size() < 1;
    }
}
