package ua.spring.dao;

import ua.spring.domain.Ticket;

import java.util.Collection;

public interface TicketDAO {

    void save(Ticket ticket);

    void remove(Ticket ticket);

    Ticket getById(Long id);

    Collection<Ticket> getAll();

}
