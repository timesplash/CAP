package org.dev.backend.CAP.controller;

import lombok.extern.slf4j.Slf4j;
import org.dev.api.CAP.model.DataDTO;
import org.dev.backend.CAP.service.DataService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@CrossOrigin
@RequestMapping("/api/data")
public class DataController {

    private final DataService dataService;

    public DataController(DataService dataService) {
        this.dataService = dataService;
    }

    @PutMapping
    void save (@RequestBody DataDTO dataDTO) {
        dataService.save(dataDTO);
    }

    @PostMapping
    public ResponseEntity<List<DataDTO>> getData(@RequestBody String username) {
        return new ResponseEntity<>(dataService.getData(username), HttpStatus.OK);
    }
}
