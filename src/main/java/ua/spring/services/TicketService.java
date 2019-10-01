package ua.spring.services;

import ua.spring.domain.Ticket;

import java.util.Collection;

public interface TicketService {
    void save(Ticket ticket);

    void remove(Ticket ticket);

    Ticket getById(Long id);

    Collection<Ticket> getAll();
}
