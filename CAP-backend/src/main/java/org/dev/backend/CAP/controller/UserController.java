package org.dev.backend.CAP.controller;

import lombok.extern.slf4j.Slf4j;
import org.dev.backend.CAP.service.UserService;
import org.dev.backend.CAP.model.User;
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
        userService.saveUser(user);
    }

    @DeleteMapping(value = "/{login}")
    public ResponseEntity<HttpStatus> deleteUser(@PathVariable("login") String login) {
        try {
            log.info("Deleting user. Great success!");
            userService.deleteUser(login);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Exception e) {
            log.info("Deleting user. WHAT?!?! YOU LIED TO ME! You lied about California!");
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping(value = "/{login}")
    public ResponseEntity<User> findByLogin(@PathVariable("login") String login) throws Exception {
        User user = userService.findByLogin(login);
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<Boolean> findAny() {
        return new ResponseEntity<>(userService.findAny(), HttpStatus.OK);
    }
}

