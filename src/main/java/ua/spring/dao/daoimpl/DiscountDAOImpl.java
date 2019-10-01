package ua.spring.dao.daoimpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import ua.spring.DiscountStrategy;
import ua.spring.domain.Event;
import ua.spring.domain.User;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.time.LocalDateTime;

@Component
public class DiscountDAOImpl {

    private DiscountStrategy discountStrategy;
    private JdbcTemplate jdbcTemplate;

    public DiscountDAOImpl() {
    }

    @Autowired
    public DiscountDAOImpl(DiscountStrategy discountStrategy, JdbcTemplate jdbcTemplate) {
        this.discountStrategy = discountStrategy;
        this.jdbcTemplate = jdbcTemplate;
    }

    //returns total discount (from 0 to 100) that can be applied to the user buying
    // specified number of tickets for specific event and air dateTime
    public byte getDiscount(@Nullable User user,
                            @Nonnull Event event,
                            @Nonnull LocalDateTime airDateTime,
                            long numberOfTickets) {
        byte discount1 = discountStrategy.getDiscountByEvery10thTickets(user);
        byte discount2 = discountStrategy.getDiscountByBirthdayStrategy(user);
        return discount1 > discount2 ? discount1 : discount2;
    }
}
