package org.dev.backend.CAP.service;

import lombok.extern.slf4j.Slf4j;
import org.dev.api.CAP.model.LogInDateDTO;
import org.dev.backend.CAP.repository.LogInDatesRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class LogInService {
    private final LogInDatesRepository logInDatesRepository;

    public LogInService(LogInDatesRepository logInDatesRepository) {
        this.logInDatesRepository = logInDatesRepository;
    }

    public void saveEntry(LogInDateDTO logInDateDTO) {
        log.info("Saving new entry date.");
        logInDatesRepository.save(logInDateDTO);
    }

    public List<LogInDateDTO> findAll() {
        log.info("Finding all dates.");
        return logInDatesRepository.findAll();
    }

    public void deleteOldUser(String login) {
        log.info("Deleting old user.");
        logInDatesRepository.delete(login);
    }
}
