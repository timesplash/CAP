package org.dev.backend.CAP.repository;

import org.dev.api.CAP.model.DataDTO;
import org.dev.api.CAP.model.RangeDTO;

import java.util.List;

public interface DataRepository {

    void save (DataDTO dataDTO);

    List<DataDTO> getData(RangeDTO rangeDTO);

    void deleteWithUser (String username);
}
