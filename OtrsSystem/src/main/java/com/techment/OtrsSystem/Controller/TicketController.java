package com.techment.OtrsSystem.Controller;

import com.techment.OtrsSystem.Service.TicketService;
import com.techment.OtrsSystem.Service.UserService;
import com.techment.OtrsSystem.domain.Ticket;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpServerErrorException;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/users/{id}")
public class TicketController {
    private static final Logger LOGGER = LoggerFactory.getLogger(UserController.class);

    public static final String INITIAL_STATUS = "pending";

    @Autowired
    private TicketService ticketService;

    @Autowired
    private UserService userService;

    @PostMapping("/ticket")
    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_CSR') or hasRole('ROLE_USER')")
    @ResponseStatus(HttpStatus.CREATED)
    public Optional<Ticket> raiseTicket(@RequestBody @Valid TicketDto ticketDto, @PathVariable("id") Long id){
        ticketDto.setStatus(INITIAL_STATUS);
        return Optional.ofNullable(ticketService.createTicket(ticketDto.getCategory(), ticketDto.getDescription(), ticketDto.getStatus(), ticketDto.getTitle(), ticketDto.getUserId()));

    }

    @GetMapping("/tickets")
    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_CSR') or hasRole('ROLE_USER')")
    public List<Ticket> getTickets(@PathVariable("id") Long id){
        return ticketService.findTicketsByUserId(id);
    }
}
