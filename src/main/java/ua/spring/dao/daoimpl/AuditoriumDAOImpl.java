package ua.spring.dao.daoimpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import ua.spring.DataBase;
import ua.spring.dao.AuditoriumDAO;
import ua.spring.domain.Auditorium;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Component
public class AuditoriumDAOImpl implements AuditoriumDAO {

    private JdbcTemplate jdbcTemplate;

    public AuditoriumDAOImpl() {
    }

    @Autowired
    public AuditoriumDAOImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Nonnull
    @Override
    public Set<Auditorium> getAll() {
        return (Set<Auditorium>) DataBase.auditorium.values();
    }

    @Nullable
    @Override
    public Auditorium getByName(@Nonnull String name) {
        return finAuditoriumByName(name);
    }

    @Override
    public void save(@Nonnull Auditorium auditorium) {
        jdbcTemplate.update("INSERT INTO AUDITORIUM (NAME,NUMBEROFSEATS)\n" +
                        "VALUES (?,?)",
                auditorium.getName(),
                auditorium.getNumberOfSeats());
    }

    private Auditorium finAuditoriumByName(String name) {
        List<Auditorium> list = DataBase.auditorium.values().stream().
                filter(x -> x.getName().equals(name))
                .collect(Collectors.toList());
        if (list.size() != 0) {
            return list.get(0);
        }

        return null;
    }
}
