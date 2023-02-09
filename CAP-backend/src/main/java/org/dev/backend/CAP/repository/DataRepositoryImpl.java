package org.dev.backend.CAP.repository;

import org.dev.api.CAP.model.DataDTO;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.support.JdbcDaoSupport;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.List;

public class DataRepositoryImpl extends JdbcDaoSupport implements DataRepository {

    //language=SQL
    public static final String SAVE_REQUEST = "INSERT INTO data(name, date, amount, username) VALUES (?,?,?,?) ON CONFLICT DO NOTHING ";


    @Override
    public void save(DataDTO dataDTO) {

    }

    @Override
    public List<DataDTO> getData(String username) {
        return null;
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
}
