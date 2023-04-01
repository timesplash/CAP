package org.dev.backend.CAP.repository;

import lombok.extern.slf4j.Slf4j;
import org.dev.api.CAP.model.DataDTO;
import org.dev.api.CAP.model.RangeDTO;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.support.JdbcDaoSupport;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

@Slf4j
@Repository
public class DataRepositoryImpl extends JdbcDaoSupport implements DataRepository {

    //language=SQL
    private static final String SAVE_REQUEST = "INSERT INTO data(name, date, amount, username) VALUES (?,?,?,?) ON CONFLICT"+
            " DO NOTHING ";

    //language=SQL
    private static final String DELETE_WITH_USER_REQUEST = "DELETE FROM data WHERE username = ?";

    //language=SQL
    private static final String FIND_FOR_USER_REQUEST = "SELECT * FROM data WHERE username = ?";

    public DataRepositoryImpl(DataSource dataSource){
        setDataSource(dataSource);
    }


    @Override
    public void save(DataDTO dataDTO) {
        Timestamp dateTime = Timestamp.valueOf(dataDTO.getDate());
        getJdbcTemplate().update(SAVE_REQUEST,
                dataDTO.getCategoryName(),
                dateTime,
                dataDTO.getAmount(),
                dataDTO.getUsername());
    }


    @Override
    public List<DataDTO> getData(RangeDTO rangeDTO) {
        List<DataDTO> dataDTOList = getJdbcTemplate().query(FIND_FOR_USER_REQUEST,
                new DataRowMapper(),rangeDTO.getUserName());
        List<DataDTO> dataSorted = new ArrayList<>();
        for (DataDTO dataDTO: dataDTOList) {
            if (dataDTO.getDate().getYear() == rangeDTO.getYear()
                    && dataDTO.getDate().getMonth().getValue() == rangeDTO.getMonth()) {
                dataSorted.add(dataDTO);
            }
        }
        LocalDateTimeComparator comparator = new LocalDateTimeComparator();
        dataSorted.sort((o1, o2) -> comparator.compare(o1.getDate(),o2.getDate()));
        return dataSorted;
    }

    @Override
    public void deleteWithUser(String username) {
        getJdbcTemplate().update(DELETE_WITH_USER_REQUEST,username);
    }

    private static class DataRowMapper implements RowMapper<DataDTO> {

        @Override
        public DataDTO mapRow(ResultSet rs, int rowNum) throws SQLException {
            return DataDTO.builder()
                    .categoryName(rs.getString(3))
                    .date(rs.getTimestamp(2).toLocalDateTime())
                    .amount(rs.getDouble(4))
                    .username(rs.getString(5))
                    .build();
        }
    }

    private static class LocalDateTimeComparator implements Comparator<LocalDateTime> {
        @Override
        public int compare(LocalDateTime o1, LocalDateTime o2) {
            int result = o1.toLocalDate().compareTo(o2.toLocalDate());
            result = (-1) * result;
            if (0 == result) {
                result = o1.toLocalTime().compareTo(o2.toLocalTime());
                result = (-1) * result;
            }

            return result;

        }


    }
}
