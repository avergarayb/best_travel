package com.sendtrust.best_travel.domain.repositories.jpa;

import com.sendtrust.best_travel.domain.entities.jpa.ReservationEntity;
import org.springframework.data.repository.CrudRepository;

import java.util.UUID;

public interface ReservationRepository extends CrudRepository<ReservationEntity, UUID> {
}
