package org.dev.backend.CAP.repository;

import org.dev.api.CAP.model.DataDTO;

import java.util.List;

public interface DataRepository {

    void save (DataDTO dataDTO);

    List<DataDTO> getData(String username);
}
