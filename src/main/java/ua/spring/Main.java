package ua.spring;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import ua.spring.aspects.CounterAspect;
import ua.spring.aspects.DiscountAspect;
import ua.spring.domain.*;
import ua.spring.services.*;
import ua.spring.services.servicesimpl.*;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

public class Main {
    public static Map<Long, User> userMap = new HashMap<>();
    public static Map<Long, Event> eventMap = new HashMap<>();
    public static Map<Long, Ticket> ticketMap = new HashMap<>();

    public static void main(String[] args) {

        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(Config.class);

        Auditorium auditorium = context.getBean("auditorium", Auditorium.class);
        System.out.println("auditorium = " + auditorium);
        AuditoriumService auditoriumService = context.getBean("auditoriumServiceImpl", AuditoriumServiceImpl.class);
        auditoriumService.save(auditorium);

        Event event = new Event("Terminator", "20", EventRating.MID);
        Event event2 = new Event("Terminator2", "30", EventRating.HIGH);
        event.addAirDateTime(LocalDateTime.now());
        event.assignAuditorium(event.getAirDates().first(), auditorium);
        EventService eventService = context.getBean("eventServiceImpl", EventServiceImpl.class);
        eventService.save(event);
        eventService.save(event2);
        putAllEventsToMap(eventService.getAll());

        User user = new User("Pasen", "Lario", "dig@ukr.net", "1986-05-13");
        User user2 = new User("Vito", "Megal", "op@ukr.net", "1989-06-25");
        UserService userService = context.getBean("userServiceImpl", UserServiceImpl.class);
        userService.save(user);
        userService.save(user2);
        putAllUsersToMap(userService.getAll());

        Ticket ticket1 = new Ticket(user, event, event.getAirDates().first(), 1);
        Ticket ticket2 = new Ticket(user, event, event.getAirDates().first(), 2);
        Ticket ticket3 = new Ticket(user2, event, event.getAirDates().first(), 3);
        TicketService ticketService = context.getBean("ticketServiceImpl", TicketServiceImpl.class);
        ticketService.save(ticket1);
        ticketService.save(ticket2);
        ticketService.save(ticket3);
        putAllTicketsToMap(ticketService.getAll());

        BookingService bookingService = context.getBean("bookingServiceImpl", BookingServiceImpl.class);
        Set<Ticket> tickets = new HashSet<>(Arrays.asList(ticket1, ticket2, ticket3));
        bookingService.bookTickets(tickets);
        bookingService.getTicketsPrice(
                event,
                event.getAirDates().first(),
                user,
                tickets.stream().map(Ticket::getSeat).collect(Collectors.toSet()));

        DiscountService discountService = context.getBean("discountServiceImpl", DiscountServiceImpl.class);
        discountService.getDiscount(user, event, event.getAirDates().first(), 2);
        discountService.getDiscount(user2, event, event.getAirDates().first(), 1);

        printCountEventWasAccessedByName(CounterAspect.counterEventWasAccessedByName);
        printCountPricesWereRequired(CounterAspect.counterPricesWereRequired);
        printCountTicketsWereBooked(CounterAspect.counterTicketsWereBooked);
        printHowManyTimesDiscountWasGivenToUser(DiscountAspect.howManyTimesDiscountWasGivenForUser);
        printAllGivenDiscounts(DiscountAspect.discountSum);

        context.close();
    }

    private static void printCountEventWasAccessedByName(Map<Event, Integer> list) {
        for (Event event : list.keySet()) {
            System.out.println("Event [" + event.getName() + "] Was Accessed ByName = " + list.get(event) + " times.");
        }
    }

    private static void printCountPricesWereRequired(Map<Event, Integer> list) {
        for (Event event : list.keySet()) {
            System.out.println("Price to Event [" + event.getName() + "] Were Required = " + list.get(event) + " times.");
        }
    }

    private static void printCountTicketsWereBooked(Map<Event, Integer> list) {
        for (Event event : list.keySet()) {
            System.out.println("Tickets to Event [" + event.getName() + "] Were Booked = " + list.get(event) + " times.");
        }
    }

    private static void printHowManyTimesDiscountWasGivenToUser(Map<User, Integer> list) {
        for (User user : list.keySet()) {
            System.out.println("Discount Was Given To User = " + user.getFirstName() + " -> " + list.get(user) + " times.");
        }
    }

    private static void printAllGivenDiscounts(Integer sum) {
        System.out.println("Discount Was Given To All Users = " + sum + " times.");
    }

    private static void putAllUsersToMap(Collection<User> users) {
        for (User user : users) {
            userMap.put(user.getId(), user);
        }
    }

    private static void putAllEventsToMap(Collection<Event> events) {
        for (Event event : events) {
            eventMap.put(event.getId(), event);
        }
    }

    private static void putAllTicketsToMap(Collection<Ticket> tickets) {
        for (Ticket ticket : tickets) {
            ticketMap.put(ticket.getId(), ticket);
        }
    }
}
