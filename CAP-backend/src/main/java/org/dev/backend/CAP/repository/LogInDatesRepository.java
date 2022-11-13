package org.dev.backend.CAP.repository;

import org.dev.api.CAP.model.LogInDateDTO;

import java.util.List;

public interface LogInDatesRepository {
    void save (LogInDateDTO logInDateDTO);

    List<LogInDateDTO> findAll();

    void delete (String login);

}
