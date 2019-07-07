package com.techment.OtrsSystem.Service;

import com.techment.OtrsSystem.Repository.TicketRepository;
import com.techment.OtrsSystem.Repository.UserRepository;
import com.techment.OtrsSystem.domain.Ticket;
import com.techment.OtrsSystem.domain.User;
import jdk.nashorn.internal.runtime.options.Option;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class TicketService {

    public static final long MAX_DAYS = 8;
    private static final Logger LOGGER = LoggerFactory.getLogger(TicketService.class);

    @Autowired
    private TicketRepository ticketRepository;

    @Autowired
    private UserRepository userRepository;

    public Ticket createTicket(String category, String description, String status, String title, Long id){
        LOGGER.info("New user attempting raise ticket");
        Ticket ticket = null;
        if(userRepository.existsById(id)) {
            ticket = ticketRepository.save(new Ticket(category, title, description, Timestamp.valueOf(LocalDateTime.now()), Timestamp.valueOf(LocalDateTime.now().plusDays(MAX_DAYS)), status, userRepository.getOne(id)));
        }
        return ticket;

    }

    public List<Ticket> findTicketsByUserId (Long id) {
        List<Ticket> ticket = null;
        if(userRepository.existsById(id)) {
            return ticketRepository.findByUser(userRepository.getOne(id));
        }
        return ticket;

    }
}
