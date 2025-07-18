package com.sendtrust.best_travel.domain.repositories.jpa;

import com.sendtrust.best_travel.domain.entities.jpa.TicketEntity;
import org.springframework.data.repository.CrudRepository;

import java.util.UUID;

public interface TicketRepository extends CrudRepository<TicketEntity, UUID> {
}
