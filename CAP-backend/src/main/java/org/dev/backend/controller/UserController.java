package org.dev.backend.controller;

import lombok.extern.slf4j.Slf4j;
import org.dev.backend.model.User;
import org.dev.backend.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@CrossOrigin
@RequestMapping("/api/users")
public class UserController {
    private final UserService userService;


    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PutMapping("/new_user")
    void saveUser(@RequestBody User user) {
        log.info("Saving new user...");
        userService.saveUser(user);
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<HttpStatus> deleteUser(@PathVariable("id") int id) {
        try {
            log.info("Deleting user. Great success!");
            userService.deleteUser(id);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Exception e) {
            log.info("Deleting user. WHAT?!?! YOU LIED TO ME! You lied about California!");
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping(value = "/{login}")
    public ResponseEntity<User> findByLogin(@PathVariable("login") String login) throws Exception {
        log.info("Finding user by login = " + login);
        return new ResponseEntity<>(userService.findByLogin(login), HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<Boolean> findAny() {
        log.info("Finding all users");
        return new ResponseEntity<>(userService.findAny(), HttpStatus.OK);
    }
}

