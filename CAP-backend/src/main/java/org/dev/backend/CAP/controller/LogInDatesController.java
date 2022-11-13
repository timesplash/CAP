package org.dev.backend.CAP.controller;

import lombok.extern.slf4j.Slf4j;
import org.dev.api.CAP.model.LogInDateDTO;
import org.dev.backend.CAP.service.LogInService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@CrossOrigin
@RequestMapping("api/login_dates")
public class LogInDatesController {
    private final LogInService logInService;

    public LogInDatesController(LogInService logInService) {
        this.logInService = logInService;
    }

    @PutMapping
    void saveEntry (@RequestBody LogInDateDTO logInDateDTO) {
        logInService.saveEntry(logInDateDTO);
    }

    @GetMapping
    public ResponseEntity<List<LogInDateDTO>> findAll() {
        return new ResponseEntity<>(logInService.findAll(), HttpStatus.OK);
    }

    @DeleteMapping(value = "/{login}")
    public ResponseEntity<HttpStatus> deleteUser(@PathVariable("login") String login) {
        logInService.deleteOldUser(login);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
