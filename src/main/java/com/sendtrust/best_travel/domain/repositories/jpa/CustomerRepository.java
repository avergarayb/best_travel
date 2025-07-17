package com.sendtrust.best_travel.domain.repositories.jpa;

import com.sendtrust.best_travel.domain.entities.jpa.CustomerEntity;
import org.springframework.data.repository.CrudRepository;

public interface CustomerRepository extends CrudRepository<CustomerEntity, String> {
}
