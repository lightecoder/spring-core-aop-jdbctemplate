package ua.spring;

import org.springframework.stereotype.Component;
import ua.spring.domain.User;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.logging.Logger;

@Component
public class DiscountStrategy {
    private static Logger logger = Logger.getLogger(DiscountStrategy.class.getName());

    public byte getDiscountByEvery10thTickets(User user) {
        byte discount = 0;
        int ticketsCount = (int) DataBase.tickets.values().stream()
                .filter(x -> x.getUser().equals(user))
                .count();
        if (ticketsCount % 10 == 0 && ticketsCount > 0) {
            discount = 50;
        }
        return discount;
    }

    public byte getDiscountByBirthdayStrategy(User user) {
        byte discount = 0;
        String dateOfBirth = user.getDateOfBirth().substring(4);
        LocalDateTime localDateTimeEvent = user.getTickets().last().getDateTime();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        String formattedString = localDateTimeEvent.format(formatter).substring(4);
        if (dateOfBirth.equals(formattedString)) {
            discount = 50;
            return discount;
        }
        return discount;
    }


}
