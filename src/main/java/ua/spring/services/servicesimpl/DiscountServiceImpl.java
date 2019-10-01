package ua.spring.services.servicesimpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ua.spring.dao.daoimpl.DiscountDAOImpl;
import ua.spring.domain.Event;
import ua.spring.domain.User;
import ua.spring.services.DiscountService;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.time.LocalDateTime;

@Component
public class DiscountServiceImpl implements DiscountService {

    private DiscountDAOImpl discountDAO;

    public DiscountServiceImpl() {
    }

    @Autowired
    public DiscountServiceImpl(DiscountDAOImpl discountDAO) {
        this.discountDAO = discountDAO;
    }

    //returns total discount (from 0 to 100) that can be applied to the user buying
    // specified number of tickets for specific event and air dateTime
    @Override
    public byte getDiscount(@Nullable User user, @Nonnull Event event, @Nonnull LocalDateTime airDateTime,
                            long numberOfTickets) {
        return discountDAO.getDiscount(user, event, airDateTime, numberOfTickets);
    }
}
