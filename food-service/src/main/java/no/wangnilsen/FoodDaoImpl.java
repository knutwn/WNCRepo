package no.wangnilsen;

import no.wangnilsen.vo.Matrett;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Service;

import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;

@Service
public class FoodDaoImpl implements FoodDao {

    JdbcTemplate jdbcTemplate;

    @Autowired
    DataSource dataSource;

    public Matrett getMatrett(int id) {
        String sql = "SELECT id, navn, url FROM Matrett WHERE id = ?";
        return jdbcTemplate.queryForObject(sql, new Object[]{id}, new MatrettMapper());
    }

    public void insertMatrett(int id, String navn, String url) {
        jdbcTemplate = new JdbcTemplate(dataSource);
        String sql = "INSERT INTO Matrett VALUES(?,?,?)";
        jdbcTemplate.update(sql, id, navn, url);
    }


    public class MatrettMapper implements RowMapper<Matrett> {
        public Matrett mapRow(ResultSet rs, int rowNum) throws SQLException {
            return new Matrett(rs.getInt("id"), rs.getString("navn"), rs.getString("url"));
        }
    }

}
