package ua.spring.dao.daoimpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import ua.spring.DataBase;
import ua.spring.Main;
import ua.spring.dao.BookingDAO;
import ua.spring.domain.Event;
import ua.spring.domain.Ticket;
import ua.spring.domain.User;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.time.LocalDateTime;
import java.util.Set;
import java.util.stream.Collectors;

@Component
public class BookingDAOImpl implements BookingDAO {

    private JdbcTemplate jdbcTemplate;

    public BookingDAOImpl() {
    }

    @Autowired
    public BookingDAOImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    /**
     * returns total price for buying all tickets for specified events on specific date and time for specified seats.
     *     User is needed to calculate discount (see below)
     *     Event is needed to get base price, rating
     *     Vip seats should cost more than regular seats (For example, 2xBasePrice)
     *     All prices for high rated movies should be higher (For example, 1.2xBasePrice)
      */

    @Override
    public double getTicketsPrice(@Nonnull Event event,
                                  @Nonnull LocalDateTime dateTime,
                                  @Nullable User user,
                                  @Nonnull Set<Long> seats) {
        System.out.println("in method getTicketsPrice");
        Set<Ticket> ticketsList = DataBase.tickets.values().stream()
                .filter(x -> x.getUser().equals(user))
                .filter(x -> x.getEvent().equals(event))
                .filter(x -> x.getDateTime().equals(dateTime))
                .collect(Collectors.toSet());
        double ticketPrice = 0;
        for (Ticket t : ticketsList) {
            ticketPrice += Integer.valueOf(t.getEvent().getBasePrice());
        }
        return ticketPrice;
    }

    /**
     *  Ticket should contain information about events, air dateTime, seat, and users. The users could be registered or not.
     *  Purchased tickets for particular events should be stored.
      */

    @Override
    public void bookTickets(@Nonnull Set<Ticket> tickets) {
        for (Ticket t : tickets) {
            t.getUser().addTicket(t);
            jdbcTemplate.update("INSERT INTO BOOKED_TICKETS (USER_ID, TICKET_ID, EVENT_ID)" +
                            " VALUES (?, ?, ?)",
                    t.getUser().getId(),
                    t.getId(),
                    getEventId(t));
        }
    }

    //get all purchased tickets for events for specific date and Time
    @Nonnull
    @Override
    public Set<Ticket> getPurchasedTicketsForEvent(@Nonnull Event event, @Nonnull LocalDateTime dateTime) {
        return DataBase.tickets.values().stream()
                .filter(x -> x.getEvent().equals(event))
                .filter(x -> x.getDateTime().equals(dateTime))
                .collect(Collectors.toSet());
    }

    private long getEventId(Ticket ticket){
        return Main.ticketMap.values().stream()
                .filter(x->x.getEvent().getName().equals(ticket.getEvent().getName()))
                .findFirst()
                .orElse(new Ticket())
                .getEvent().getId();
    }
}
