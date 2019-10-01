package ua.spring.services.servicesimpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ua.spring.dao.TicketDAO;
import ua.spring.domain.Ticket;
import ua.spring.services.TicketService;

import java.util.Collection;

@Component
public class TicketServiceImpl implements TicketService {

    private TicketDAO ticketDAO;

    public TicketServiceImpl() {
    }

    @Autowired
    public TicketServiceImpl(TicketDAO ticketDAO) {
        this.ticketDAO = ticketDAO;
    }

    @Override
    public void save(Ticket ticket) {
        ticketDAO.save(ticket);
    }

    @Override
    public void remove(Ticket ticket) {

    }

    @Override
    public Ticket getById(Long id) {
        return null;
    }

    @Override
    public Collection<Ticket> getAll() {
        return ticketDAO.getAll();
    }
}
