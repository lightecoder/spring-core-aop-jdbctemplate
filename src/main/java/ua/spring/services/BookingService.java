package ua.spring.services;

import ua.spring.domain.Event;
import ua.spring.domain.Ticket;
import ua.spring.domain.User;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.time.LocalDateTime;
import java.util.Set;

public interface BookingService {

    /**
     * Getting price when buying all supplied seats for particular events
     * 
     * @param event
     *            Event to get base tickets price, vip seats and other
     *            information
     * @param dateTime
     *            Date and time of events air
     * @param user
     *            User that buys tickets could be needed to calculate discount.
     *            Can be <code>null</code>
     * @param seats
     *            Set of seat numbers that users wants to buy
     * @return total price
     */
    public double getTicketsPrice(@Nonnull Event event, @Nonnull LocalDateTime dateTime, @Nullable User user,
                                  @Nonnull Set<Long> seats);

    /**
     * Books tickets in internal system. If users is not
     * <code>null</code> in a tickets then booked tickets are saved with it
     * 
     * @param tickets
     *            Set of tickets
     */
    public void bookTickets(@Nonnull Set<Ticket> tickets);

    /**
     * Getting all purchased tickets for events on specific air date and time
     * 
     * @param event
     *            Event to get tickets for
     * @param dateTime
     *            Date and time of airing of events
     * @return set of all purchased tickets
     */
    public @Nonnull Set<Ticket> getPurchasedTicketsForEvent(@Nonnull Event event, @Nonnull LocalDateTime dateTime);

}
