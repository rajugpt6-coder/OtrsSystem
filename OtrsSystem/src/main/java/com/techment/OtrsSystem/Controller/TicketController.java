package com.techment.OtrsSystem.Controller;

import com.techment.OtrsSystem.Service.CustomerServiceRepresentativeService;
import com.techment.OtrsSystem.Service.TicketService;
import com.techment.OtrsSystem.Service.UserService;
import com.techment.OtrsSystem.domain.Ticket;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
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
    CustomerServiceRepresentativeService customerServiceRepresentativeService;

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

    @GetMapping("ticket/{ticketId}")
    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_CSR') or hasRole('ROLE_USER')")
    public Ticket getTicketDetails (@PathVariable("ticketId") Long ticketId) {
        return ticketService.findTicketById(ticketId);

    }

    @GetMapping("/csr/assignedTickets")
    @PreAuthorize("hasRole('ROLE_CSR')")
    public List<Ticket> getTicketsByDepartment(@PathVariable("id") Long id){
        return ticketService.getTicketsByCategory(userService.getCustomerServiceRepresentative(id).getDepartment(), INITIAL_STATUS);
    }

    @PatchMapping("/csr/resolveTicket")
    @PreAuthorize("hasRole('ROLE_CSR')")
    @ResponseStatus(HttpStatus.CREATED)
    public void resolveIssue(@PathVariable("id") Long id, @RequestBody @Validated TicketDto ticketDto) {
        ticketService.resolveIssue(id, ticketDto.getUserId());
    }

    @PatchMapping("/tickets/{ticketId}")
    @PreAuthorize("hasRole('ROLE_CSR')")
    @ResponseStatus(HttpStatus.CREATED)
    public void updateStatus(@PathVariable("ticketId") long id, @RequestBody @Validated TicketDto ticketDto){
        ticketService.updateStatus(ticketDto.getStatus(), id);

    }

    @GetMapping("/tickets/all")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public List<Ticket> getAllTickets() {
        return ticketService.getAllTickets();
    }

}
