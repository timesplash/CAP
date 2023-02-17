package org.dev.backend.CAP.repository;

import lombok.extern.slf4j.Slf4j;
import org.dev.api.CAP.model.LogInDateDTO;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.support.JdbcDaoSupport;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.List;

@Slf4j
@Repository
public class LogInDatesRepositoryImpl extends JdbcDaoSupport implements LogInDatesRepository {

    //language=SQL
    private static final String SAVE_NEW_ENTRY = "INSERT INTO log_in_date (user_id, date) VALUES (?,?)" +
            " ON CONFLICT (user_id) DO UPDATE SET date = EXCLUDED.date";

    //language=SQL
    private static final String FIND_ALL = "SELECT * FROM log_in_date";

    //language=SQL
    private static final String DELETE_BY_ID = "DELETE FROM log_in_date WHERE user_id = ?";

    private final UserRepository userRepository;

    private final CategoriesRepository categoriesRepository;

    private final DataRepository dataRepository;

    public LogInDatesRepositoryImpl(DataSource dataSource, UserRepository userRepository, CategoriesRepository categoriesRepository, DataRepository dataRepository) {
        this.userRepository = userRepository;
        this.categoriesRepository = categoriesRepository;
        this.dataRepository = dataRepository;
        setDataSource(dataSource);
    }

    @Override
    public void save(LogInDateDTO logInDateDTO) {
        Timestamp dateTime = Timestamp.valueOf(logInDateDTO.getDate());
        getJdbcTemplate().update(SAVE_NEW_ENTRY, logInDateDTO.getLogin(), dateTime);
    }

    @Override
    public List<LogInDateDTO> findAll() {
        return getJdbcTemplate().query(FIND_ALL, new LogInDatesMapper());
    }

    @Override
    public void delete(String login) {
        getJdbcTemplate().update(DELETE_BY_ID, login);
        categoriesRepository.delete(login);
        userRepository.delete(login);
        dataRepository.deleteWithUser(login);
    }

    private static class LogInDatesMapper implements RowMapper<LogInDateDTO> {

        @Override
        public LogInDateDTO mapRow(ResultSet rs, int rowNum) throws SQLException {
            return LogInDateDTO.builder()
                    .login(rs.getString(2))
                    .date(rs.getTimestamp(3).toLocalDateTime())
                    .build();
        }
    }
}
