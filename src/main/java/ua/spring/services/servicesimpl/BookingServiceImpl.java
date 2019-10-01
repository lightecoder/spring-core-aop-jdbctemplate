package ua.spring.services.servicesimpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ua.spring.dao.BookingDAO;
import ua.spring.domain.Event;
import ua.spring.domain.Ticket;
import ua.spring.domain.User;
import ua.spring.services.BookingService;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.time.LocalDateTime;
import java.util.Set;

@Component
public class BookingServiceImpl implements BookingService {

    private BookingDAO bookingDAO;

    @Autowired
    public BookingServiceImpl(BookingDAO bookingDAO) {
        this.bookingDAO = bookingDAO;
    }

    public BookingServiceImpl() {
    }

    @Override
    public double getTicketsPrice(@Nonnull Event event,
                                  @Nonnull LocalDateTime dateTime,
                                  @Nullable User user,
                                  @Nonnull Set<Long> seats) {
        return bookingDAO.getTicketsPrice(event, dateTime, user, seats);
    }

    @Override
    public void bookTickets(@Nonnull Set<Ticket> tickets) {
        bookingDAO.bookTickets(tickets);
        tickets.forEach(x->x.getUser().addTicket(x));
    }

    @Nonnull
    @Override
    public Set<Ticket> getPurchasedTicketsForEvent(@Nonnull Event event, @Nonnull LocalDateTime dateTime) {
        return bookingDAO.getPurchasedTicketsForEvent(event, dateTime);
    }
}
