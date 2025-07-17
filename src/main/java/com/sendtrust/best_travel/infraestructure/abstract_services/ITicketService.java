package com.sendtrust.best_travel.infraestructure.abstract_services;

import com.sendtrust.best_travel.api.models.request.TicketRequest;
import com.sendtrust.best_travel.api.models.responses.TicketResponse;

import java.math.BigDecimal;
import java.util.Currency;
import java.util.UUID;

public interface ITicketService extends CrudService<TicketRequest, TicketResponse, UUID>{

    BigDecimal findPrice(Long flyId, Currency currency);
}
