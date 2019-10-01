package ua.spring;

public class Helper {

    private static Long eventId = 0L;
    private static Long ticketId = 0L;
    private static Long userId = 0L;

    private Helper() {
    }

    public static Long getNextId(String id) {
        switch (id) {
            case "events": {
                return eventId++;
            }
            case "tickets": {
                return ticketId++;
            }
            case "users": {
               return userId++;
            }
            default: {
                throw new RuntimeException("Domain object with name "+ id + " doesn't exist.");
            }
        }
    }

}
