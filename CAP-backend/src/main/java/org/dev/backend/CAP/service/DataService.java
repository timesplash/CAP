package org.dev.backend.CAP.service;

import lombok.extern.slf4j.Slf4j;
import org.dev.api.CAP.model.DataDTO;
import org.dev.backend.CAP.repository.DataRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class DataService {
    private final DataRepository dataRepository;

    public DataService(DataRepository dataRepository) {
        this.dataRepository = dataRepository;
    }

    public void save(DataDTO dataDTO) {
        log.info("Saving transfer info");
        dataRepository.save(dataDTO);
    }

    public List<DataDTO> getData(String username) {
        log.info("Getting transfers for user: " + username);
        return dataRepository.getData(username);
    }
}
