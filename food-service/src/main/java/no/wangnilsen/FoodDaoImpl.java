package no.wangnilsen;

import no.wangnilsen.vo.Matrett;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Service;

import javax.persistence.NonUniqueResultException;
import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

/**
 * FooDaoImpl
 */
@Service
public class FoodDaoImpl implements FoodDao {

    /**
     * Jdbc Template
     */
    JdbcTemplate jdbcTemplate;

    /**
     * Data Source
     */
    @Autowired
    DataSource dataSource;

    public Matrett getMatrett(final int id) {
        final String sql = "SELECT id, navn, url FROM Matrett WHERE id = ?";
        final List<Matrett> matrettList = jdbcTemplate.query(sql, new MatrettMapper(), id);
        if(matrettList.size() > 1) {
            throw new NonUniqueResultException("Matrett id: " + id + " er ikke unik");
        }
        return matrettList.get(0);
    }

    public void insertMatrett(final int id, final String navn, final String url) {
        jdbcTemplate = new JdbcTemplate(dataSource);
        final String sql = "INSERT INTO Matrett VALUES(?,?,?)";
        jdbcTemplate.update(sql, id, navn, url);
    }


    public static class MatrettMapper implements RowMapper<Matrett> {
        public Matrett mapRow(final ResultSet rs, final int rowNum) throws SQLException {
            return new Matrett(
                    rs.getInt("id"),
                    rs.getString("navn"),
                    rs.getString("url"));
        }
    }

}
