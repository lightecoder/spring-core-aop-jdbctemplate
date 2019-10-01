package ua.spring.aspects;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.stereotype.Component;
import ua.spring.DataBase;
import ua.spring.domain.Event;
import ua.spring.domain.Ticket;

import javax.annotation.Nonnull;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.stream.Stream;

@Aspect
@Component
public class CounterAspect {
    public static Map<Event, Integer> counterEventWasAccessedByName = new HashMap<>();
    public static Map<Event, Integer> counterPricesWereRequired = new HashMap<>();
    public static Map<Event, Integer> counterTicketsWereBooked = new HashMap<>();

    @Pointcut("execution(* findEventByName(*))")
    private void eventWasAccessedByName() {
    }

    @Before("eventWasAccessedByName()")
    public void beforeEventWasAccessedByName() {
        System.out.println("before method findEventByName(...)");
    }

    @AfterReturning(
            pointcut = "eventWasAccessedByName()",
            returning = "event")
    public void afterEventWasAccessedByName(Event event) {
        System.out.println("after method findEventByName(...)");
        if (event != null) {
            executeAspect(counterEventWasAccessedByName, event);
        }

    }

    @Before("eventWasAccessedByName()")
    public void eventWasAccessedByName(JoinPoint joinPoint) {
        String eventName = (String) Stream.of(joinPoint.getArgs())
                .findFirst()
                .orElse(null);
        Event event = DataBase.events.values().stream()
                .filter(x -> x.getName().equals(eventName))
                .findFirst()
                .orElse(null);
    }

    // avoiding duplicate counting because of the same name of method in different classes
    @Pointcut("execution(* ua.BookingDAOImpl.getTicketsPrice(..))")
    private void eventPricesWereRequired() {
    }

    @Around("eventPricesWereRequired()")
    public Object pricesWereRequired(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
        System.out.println("in aspect method getTicketsPrice before");
        Event event = (Event) Stream.of(proceedingJoinPoint.getArgs()).
                filter(x -> x.getClass().getSimpleName().equals("Event"))
                .findFirst()
                .orElse(null);
        executeAspect(counterPricesWereRequired, event);
        Object o = proceedingJoinPoint.proceed();
        System.out.println("in aspect method getTicketsPrice after");
        return o;
    }

    @Pointcut("execution(* ua.BookingDAOImpl.bookTickets(*))")
    private void eventTicketsWereBooked() {
    }

    @Before("eventTicketsWereBooked()")
    public void ticketsWereBooked(JoinPoint joinPoint) {
        Set<Ticket> tickets = (Set<Ticket>) Arrays.stream(joinPoint.getArgs()).findFirst().orElse(null);
        tickets.forEach(x -> executeAspect(counterTicketsWereBooked, x.getEvent()));
    }

    private void executeAspect(Map<Event, Integer> list, @Nonnull Event event) {
        Integer countEvent;
        if (list.containsKey(event)) {
            countEvent = list.get(event);
            ++countEvent;
            list.put(event, countEvent);
        } else {
            countEvent = 1;
            list.put(event, countEvent);
        }
    }

}
