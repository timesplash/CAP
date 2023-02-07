package org.dev.backend.CAP.repository;

import lombok.extern.slf4j.Slf4j;
import org.dev.api.CAP.enums.Range;
import org.dev.api.CAP.enums.Type;
import org.dev.api.CAP.model.CategoriesDTO;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.support.JdbcDaoSupport;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Slf4j
@Repository
public class CategoriesRepositoryImpl extends JdbcDaoSupport implements CategoriesRepository {
    //language=SQL
    private static final String SAVE_REQUEST = "INSERT INTO categories (id, name, type, range, user_id) VALUES " +
            "(?,?,?,?,(SELECT id FROM users WHERE login = ?)) ON CONFLICT (name) DO UPDATE SET range = EXCLUDED.range";

    //language=SQL
    private static final String GET_ALL_CATEGORIES = "SELECT * FROM categories";

    //Кажется хуйню какую то написал... upd: А может и не хуйню...
    //language=SQL
    private static final String DELETE_CATEGORIES_OF_USER = "DELETE FROM categories USING users " +
            "AS us JOIN categories c on us.id = c.user_id WHERE us.login = ?";

    private final UserRepository userRepository;

    public CategoriesRepositoryImpl(DataSource dataSource, UserRepository userRepository) {
        setDataSource(dataSource);
        this.userRepository = userRepository;
    }

    @Override
    public void save(CategoriesDTO categoriesDTO) {
        getJdbcTemplate().update(SAVE_REQUEST,
                categoriesDTO.getId(),
                categoriesDTO.getName(),
                categoriesDTO.getType().ordinal(),
                categoriesDTO.getRange().ordinal(),
                categoriesDTO.getUsername());
    }

    @Override
    public List<CategoriesDTO> getCategories() {
        List<CategoriesDTO> categoriesDTOS = getJdbcTemplate().query(GET_ALL_CATEGORIES,new CategoriesMapper());
        for (CategoriesDTO categoriesDTO : categoriesDTOS) {
            int nameId = Integer.parseInt(categoriesDTO.getUsername());
            String username = userRepository.findNameById(nameId);
            categoriesDTO.setUsername(username);
        }
        return categoriesDTOS;
    }

    @Override
    public void delete(String username) {
        getJdbcTemplate().update(DELETE_CATEGORIES_OF_USER,username);
    }

    private static class CategoriesMapper implements RowMapper<CategoriesDTO> {

        @Override
        public CategoriesDTO mapRow(ResultSet rs, int rowNum) throws SQLException {
            return CategoriesDTO.builder()
                    .id(rs.getInt(1))
                    .name(rs.getString(2))
                    .type(Type.values()[rs.getInt(3)])
                    .range(Range.values()[rs.getInt(4)])
                    .username(String.valueOf(rs.getInt(5)))
                    .build();
        }
    }
}
