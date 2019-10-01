package ua.spring.services;

import ua.spring.domain.Event;
import ua.spring.domain.User;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.time.LocalDateTime;


public interface DiscountService {

    /**
     * Getting discount based on some rules for users that buys some number of
     * tickets for the specific date time of the events
     *
     * @param user            User that buys tickets. Can be <code>null</code>
     * @param event           Event that tickets are bought for
     * @param airDateTime     The date and time events will be aired
     * @param numberOfTickets Number of tickets that users buys
     * @return discount value from 0 to 100
     */
    byte getDiscount(@Nullable User user,
                     @Nonnull Event event,
                     @Nonnull LocalDateTime airDateTime,
                     long numberOfTickets);

}
