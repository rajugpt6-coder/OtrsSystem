package com.techment.OtrsSystem.Repository;

import com.techment.OtrsSystem.domain.CustomerServiceRepresentative;
import com.techment.OtrsSystem.domain.Ticket;
import com.techment.OtrsSystem.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.List;
import java.util.Optional;

@RepositoryRestResource(exported = false)
public interface TicketRepository extends JpaRepository<Ticket, Long> {
    List<Ticket> findByUser(User user);
    List<Ticket> findByCategoryAndStatus(String category, String status);

    @Modifying
    @Query(value = "update Ticket ticket set ticket.status =:status where ticket.id =:id")
    void updateTicketStatus(@Param("status") String status, @Param("id") long id);

    long countTicketByCategory(String category);

    long countTicketByCategoryAndStatus(String category, String status);
}
