package ua.spring;

import ua.spring.domain.Auditorium;
import ua.spring.domain.Event;
import ua.spring.domain.Ticket;
import ua.spring.domain.User;

import java.util.HashMap;
import java.util.Map;

public class DataBase {

    // emulator for Database
    public static Map<Long, User> users = new HashMap<>();
    public static Map<Long, Event> events = new HashMap<>();
    public static Map<Long, Ticket> tickets = new HashMap<>();
    public static Map<Long, Auditorium> auditorium = new HashMap<>();


}
