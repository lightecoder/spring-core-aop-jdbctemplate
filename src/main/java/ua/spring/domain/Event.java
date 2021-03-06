package ua.spring.domain;

import ua.spring.DataBase;
import ua.spring.Helper;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;

public class Event extends DomainObject {

    private String name;

    private NavigableSet<LocalDateTime> airDates = new TreeSet<>();

    private String basePrice;

    private EventRating rating;

    private NavigableMap<LocalDateTime, Auditorium> auditoriums = new TreeMap<>();

    public Event() {
    }

    public Event(String name, String basePrice, EventRating rating) {
        this.name = name;
        this.basePrice = basePrice;
        this.rating = rating;
        super.setId(Helper.getNextId("events"));
        DataBase.events.put(this.getId(), this);
    }

    public Event(Integer id, String name, String basePrice, EventRating rating) {
        super.setId(Long.valueOf(id));
        this.name = name;
        this.basePrice = basePrice;
        this.rating = rating;
    }

    /**
     * Checks if events is aired on particular <code>dateTime</code> and assigns
     * auditorium to it.
     *
     * @param dateTime   Date and time of aired events for which to assign
     * @param auditorium Auditorium that should be assigned
     * @return <code>true</code> if successful, <code>false</code> if events is
     * not aired on that date
     */
    public boolean assignAuditorium(LocalDateTime dateTime, Auditorium auditorium) {
        if (airDates.contains(dateTime)) {
            auditoriums.put(dateTime, auditorium);
            return true;
        } else {
            return false;
        }
    }

    /**
     * Removes auditorium assignment from events
     *
     * @param dateTime Date and time to remove auditorium for
     * @return <code>true</code> if successful, <code>false</code> if not
     * removed
     */
    public boolean removeAuditoriumAssignment(LocalDateTime dateTime) {
        return auditoriums.remove(dateTime) != null;
    }

    /**
     * Add date and time of events air
     *
     * @param dateTime Date and time to add
     * @return <code>true</code> if successful, <code>false</code> if already
     * there
     */
    public boolean addAirDateTime(LocalDateTime dateTime) {
        return airDates.add(dateTime);
    }

    /**
     * Adding date and time of events air and assigning auditorium to that
     *
     * @param dateTime   Date and time to add
     * @param auditorium Auditorium to add if success in date time add
     * @return <code>true</code> if successful, <code>false</code> if already
     * there
     */
    public boolean addAirDateTime(LocalDateTime dateTime, Auditorium auditorium) {
        boolean result = airDates.add(dateTime);
        if (result) {
            auditoriums.put(dateTime, auditorium);
        }
        return result;
    }

    /**
     * Removes the date and time of events air. If auditorium was assigned to
     * that date and time - the assignment is also removed
     *
     * @param dateTime Date and time to remove
     * @return <code>true</code> if successful, <code>false</code> if not there
     */
    public boolean removeAirDateTime(LocalDateTime dateTime) {
        boolean result = airDates.remove(dateTime);
        if (result) {
            auditoriums.remove(dateTime);
        }
        return result;
    }

    /**
     * Checks if events airs on particular date and time
     *
     * @param dateTime Date and time to check
     * @return <code>true</code> events airs on that date and time
     */
    public boolean airsOnDateTime(LocalDateTime dateTime) {
        return airDates.stream().anyMatch(dt -> dt.equals(dateTime));
    }

    /**
     * Checks if events airs on particular date
     *
     * @param date Date to ckeck
     * @return <code>true</code> events airs on that date
     */
    public boolean airsOnDate(LocalDate date) {
        return airDates.stream().anyMatch(dt -> dt.toLocalDate().equals(date));
    }

    /**
     * Checking if events airs on dates between <code>from</code> and
     * <code>to</code> inclusive
     *
     * @param from Start date to check
     * @param to   End date to check
     * @return <code>true</code> events airs on dates
     */
    public boolean airsOnDates(LocalDate from, LocalDate to) {
        return airDates.stream()
                .anyMatch(dt -> dt.toLocalDate().compareTo(from) >= 0 && dt.toLocalDate().compareTo(to) <= 0);
    }

    @Override
    public String toString() {
        return "Event{" +
                "name='" + name + '\'' +
                ", airDates=" + airDates +
                ", basePrice=" + basePrice +
                ", rating=" + rating +
                ", auditoriums=" + auditoriums +
                "} " + super.toString();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public NavigableSet<LocalDateTime> getAirDates() {
        return airDates;
    }

    public void setAirDates(NavigableSet<LocalDateTime> airDates) {
        this.airDates = airDates;
    }

    public String  getBasePrice() {
        return basePrice;
    }

    public void setBasePrice(String  basePrice) {
        this.basePrice = basePrice;
    }

    public EventRating getRating() {
        return rating;
    }

    public void setRating(EventRating rating) {
        this.rating = rating;
    }

    public NavigableMap<LocalDateTime, Auditorium> getAuditoriums() {
        return auditoriums;
    }

    public void setAuditoriums(NavigableMap<LocalDateTime, Auditorium> auditoriums) {
        this.auditoriums = auditoriums;
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, airDates, basePrice, rating, auditoriums);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        Event other = (Event) obj;
        if (name == null) {
            if (other.name != null) {
                return false;
            }
        } else if (!name.equals(other.name)) {
            return false;
        }
        return true;
    }

}
