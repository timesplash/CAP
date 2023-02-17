package org.dev.backend.CAP.repository;

import lombok.extern.slf4j.Slf4j;
import org.dev.api.CAP.enums.Role;
import org.dev.backend.CAP.model.User;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.support.JdbcDaoSupport;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

@Slf4j
@Repository
public class UserRepositoryImpl extends JdbcDaoSupport implements UserRepository {

    //language=SQL
    private static final String SAVE_USER_REQUEST = "INSERT INTO users (login, password, role, question, answer)" +
            "VALUES (?,?,?,?,?) ON CONFLICT (login) DO UPDATE SET (login, password, role, question, answer)" +
            "= (EXCLUDED.login,EXCLUDED.password, EXCLUDED.role, EXCLUDED.question, EXCLUDED.answer)";

    //language=SQL
    private static final String DELETE_USER_REQUEST = "DELETE FROM users WHERE login = ?";

    //language=SQL
    private static final String FIND_BY_LOGIN = "SELECT * FROM users WHERE login = ?";

    //language=SQL
    private static final String FIND_ALL = "SELECT * FROM users";

    //language=SQL
    private static final String FIND_NAME_BY_ID = "SELECT login FROM users WHERE id = ?";

    public UserRepositoryImpl(DataSource dataSource){setDataSource(dataSource);}

    @Override
    public void save(User user) {
        getJdbcTemplate().update(SAVE_USER_REQUEST,
                user.getUsername(),
                new BCryptPasswordEncoder().encode(user.getPassword()),
                user.getRole().ordinal(),
                user.getKeyQuestion(),
                user.getKeyAnswer());
    }

    @Override
    public void delete(String login) {
        getJdbcTemplate().update(DELETE_USER_REQUEST, login);
    }

    @Override
    public Optional<User> findByLogin(String userName) {
        List<User> users = getJdbcTemplate().query(FIND_BY_LOGIN, new UserMapper(), userName);
        return users.stream().findFirst();
    }

    @Override
    public List<User> findAll() {
        return getJdbcTemplate().query(FIND_ALL, new UserMapper());
    }

    @Override
    public String findNameById(int id) {
        return getJdbcTemplate().query(FIND_NAME_BY_ID, (rs, rn) -> rs.getString(2)).toString();
    }

    private static class UserMapper implements RowMapper<User> {

        @Override
        public User mapRow(ResultSet rs, int rowNum) throws SQLException {
            return User.builder()
                    .userName(rs.getString(2))
                    .password(rs.getString(3))
                    .role(Role.values()[rs.getInt(4)])
                    .keyQuestion(rs.getString(5))
                    .keyAnswer(rs.getString(6))
                    .build();
        }
    }
}
