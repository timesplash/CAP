package org.dev.backend.CAP.service;

import lombok.extern.slf4j.Slf4j;
import org.dev.api.CAP.enums.Type;
import org.dev.api.CAP.model.CategoriesDTO;
import org.dev.api.CAP.model.DataDTO;
import org.dev.api.CAP.model.RangeDTO;
import org.dev.api.CAP.model.SummaryDTO;
import org.dev.backend.CAP.repository.DataRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class DataService {
    private final DataRepository dataRepository;

    private final CategoriesService categoriesService;

    public DataService(DataRepository dataRepository, CategoriesService categoriesService) {
        this.dataRepository = dataRepository;
        this.categoriesService = categoriesService;
    }

    public void save(DataDTO dataDTO) {
        log.info("Saving transfer info");
        dataRepository.save(dataDTO);
    }

    public List<DataDTO> getData(String username) {
        log.info("Getting transfers for user: " + username);
        return dataRepository.getData(username);
    }

    public SummaryDTO getSummary(RangeDTO rangeDTO) {
        List<DataDTO> dataDTOS = getData(rangeDTO.getUserName());
        double gains = 0.0;
        double losses = 0.0;
        double summary = 0.0;
        for (DataDTO dataDTO: dataDTOS) {
            CategoriesDTO categoriesDTO = categoriesService.getCategoryByName(dataDTO.getCategoryName());
            if (categoriesDTO != null && dataDTO.getDate().getYear() == rangeDTO.getYear()
                    && dataDTO.getDate().getMonth().getValue() == rangeDTO.getMonth()) {
                if (categoriesDTO.getType().equals(Type.GAINS)) {
                    gains += dataDTO.getAmount();
                    summary += dataDTO.getAmount();
                } else {
                    losses += dataDTO.getAmount();
                    summary -= dataDTO.getAmount();
                }
            }
        }
        return SummaryDTO.builder().gainSummary(gains).lossesSummary(losses).overAllSummary(summary).build();
    }
}
