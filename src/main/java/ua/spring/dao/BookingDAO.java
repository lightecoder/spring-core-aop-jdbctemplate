package ua.spring.dao;

import ua.spring.domain.Event;
import ua.spring.domain.Ticket;
import ua.spring.domain.User;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.time.LocalDateTime;
import java.util.Set;

public interface BookingDAO {

    double getTicketsPrice(@Nonnull Event event,
                           @Nonnull LocalDateTime dateTime,
                           @Nullable User user,
                           @Nonnull Set<Long> seats);

    void bookTickets(@Nonnull Set<Ticket> tickets);

    @Nonnull
    Set<Ticket> getPurchasedTicketsForEvent(@Nonnull Event event, @Nonnull LocalDateTime dateTime);
}
