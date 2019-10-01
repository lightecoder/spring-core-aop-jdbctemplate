package ua.spring.dao.daoimpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import ua.spring.Main;
import ua.spring.dao.TicketDAO;
import ua.spring.domain.Event;
import ua.spring.domain.Ticket;
import ua.spring.domain.User;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Date;
import java.util.HashSet;
import java.util.List;

@Component
public class TicketDAOImpl implements TicketDAO {
    private static final String INSERT = "INSERT INTO TICKET (USER_ID,EVENT_ID,DATETIME,SEAT)\n" +
            "VALUES (?,?,?,?)";
    private static final String SELECT = "select * from TICKET";
    private JdbcTemplate jdbcTemplate;

    public TicketDAOImpl() {
    }

    @Autowired
    public TicketDAOImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public void save(Ticket ticket) {
        jdbcTemplate.update(INSERT,
                Main.userMap.values().stream()
                        .filter(x -> x.equals(ticket.getUser()))
                        .findFirst()
                        .orElse(new User())
                        .getId(),
                Main.eventMap.values().stream()
                        .filter(x -> x.equals(ticket.getEvent()))
                        .findFirst()
                        .orElse(new Event())
                        .getId(),
                ticket.getDateTime(),
                ticket.getSeat());
    }

    @Override
    public void remove(Ticket ticket) {

    }

    @Override
    public Ticket getById(Long id) {
        return null;
    }

    @Override
    public Collection<Ticket> getAll() {
        List<Ticket> tickets = jdbcTemplate.query(SELECT,
                (rs, rowNum) -> {
                    Integer id = rs.getInt("ticket_id");
                    Integer userId = rs.getInt("user_id");
                    Integer eventId = rs.getInt("event_id");
                    Date date = rs.getDate("dateTime");
                    LocalDateTime localDateTime = new java.sql.Timestamp(date.getTime()).toLocalDateTime();
                    Integer seat = rs.getInt("seat");
                    return new Ticket(id,
                            Main.userMap.get(userId),
                            Main.eventMap.get(eventId),
                            localDateTime,
                            Long.valueOf(seat));
                });
        return new HashSet<>(tickets);

    }
}
