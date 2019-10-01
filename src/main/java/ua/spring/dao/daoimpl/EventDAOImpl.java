package ua.spring.dao.daoimpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import ua.spring.DataBase;
import ua.spring.dao.EventDAO;
import ua.spring.domain.Event;
import ua.spring.domain.EventRating;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.logging.Logger;

@Component
public class EventDAOImpl implements EventDAO {
    private static final Logger logger = Logger.getLogger(EventDAOImpl.class.getName());
    private static final String INSERT = "INSERT INTO EVENT (NAME,BASEPRICE,RATING)VALUES (?,?,?)";
    private static final String SELECT = "select * from event";


    private JdbcTemplate jdbcTemplate;

    public EventDAOImpl() {
    }

    @Autowired
    public EventDAOImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Nullable
    @Override
    public Event getByName(@Nonnull String name) {
        return findEventByName(name);
    }

    @Override
    public void save(@Nonnull Event event) {
        jdbcTemplate.update(INSERT,
                event.getName(),
                event.getBasePrice(),
                event.getRating().toString());
    }

    @Override
    public void remove(@Nonnull Event object) {
        DataBase.events.remove(object.getId());
    }

    @Override
    public Event getById(@Nonnull Long id) {
        return DataBase.events.get(id);
    }

    @Nonnull
    @Override
    public Collection<Event> getAll() {
        List<Event> events = jdbcTemplate.query(SELECT,
                (rs, rowNum) -> {
                    Integer id = rs.getInt("event_id");
                    String name = rs.getString("name");
                    String basePrice = rs.getString("basePrice");
                    String rating = rs.getString("rating");
                    return new Event(id, name, basePrice, set(rating));
                });
        return new HashSet<>(events);
    }

    EventRating set(String rating) {
        return rating.equals("LOW") ? EventRating.LOW :
                rating.equals("MID") ? EventRating.MID :
                        EventRating.HIGH;
    }

    public Event findEventByName(String name) {
        logger.info("in method findEventByName");
        return DataBase.events.values().stream().
                filter(x -> x.getName().equals(name))
                .findFirst()
                .orElse(null);
    }
}
