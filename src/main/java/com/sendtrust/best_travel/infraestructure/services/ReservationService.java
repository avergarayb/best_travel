package com.sendtrust.best_travel.infraestructure.services;

import com.sendtrust.best_travel.api.models.request.ReservationRequest;
import com.sendtrust.best_travel.api.models.responses.HotelResponse;
import com.sendtrust.best_travel.api.models.responses.ReservationResponse;
import com.sendtrust.best_travel.domain.entities.jpa.ReservationEntity;
import com.sendtrust.best_travel.domain.repositories.jpa.CustomerRepository;
import com.sendtrust.best_travel.domain.repositories.jpa.HotelRepository;
import com.sendtrust.best_travel.domain.repositories.jpa.ReservationRepository;
import com.sendtrust.best_travel.infraestructure.abstract_services.IReservationService;
import com.sendtrust.best_travel.infraestructure.helpers.ApiCurrencyConnectorHelper;
import com.sendtrust.best_travel.infraestructure.helpers.BlackListHelper;
import com.sendtrust.best_travel.infraestructure.helpers.CustomerHelper;
import com.sendtrust.best_travel.infraestructure.helpers.EmailHelper;
import com.sendtrust.best_travel.util.enums.Tables;
import com.sendtrust.best_travel.util.exceptions.IdNotFoundException;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Currency;
import java.util.Objects;
import java.util.UUID;

@Transactional
@Service
@Slf4j
@AllArgsConstructor
public class ReservationService implements IReservationService {

    private final HotelRepository hotelRepository;
    private final CustomerRepository customerRepository;
    private final ReservationRepository reservationRepository;
    private final CustomerHelper customerHelper;
    private final BlackListHelper blackListHelper;
    private final ApiCurrencyConnectorHelper currencyConnectorHelper;
    private final EmailHelper emailHelper;

    @Override
    public ReservationResponse create(ReservationRequest request) {
        blackListHelper.isInBlackListCustomer(request.getIdClient());
        var hotel = this.hotelRepository.findById(request.getIdHotel()).orElseThrow(() -> new IdNotFoundException("hotel"));
        var customer = this.customerRepository.findById(request.getIdClient()).orElseThrow(() -> new IdNotFoundException("customer"));

        var reservationToPersist = ReservationEntity.builder()
                .id(UUID.randomUUID())
                .hotel(hotel)
                .customer(customer)
                .totalDays(request.getTotalDays())
                .dateTimeReservation(LocalDateTime.now())
                .dateStart(LocalDate.now())
                .dateEnd(LocalDate.now().plusDays(request.getTotalDays()))
                .price(hotel.getPrice().add(hotel.getPrice().multiply(charges_price_percentage)))
                .build();

        var reservationPersisted = reservationRepository.save(reservationToPersist);

        this.customerHelper.incrase(customer.getDni(), ReservationService.class);

        if(Objects.nonNull(request.getEmail())) this.emailHelper.sendMail(request.getEmail(), customer.getFullName(), Tables.reservation.name());
        return this.entityToResponse(reservationPersisted);
    }

    @Override
    public ReservationResponse read(UUID id) {
        var reservationFromDB = this.reservationRepository.findById(id).orElseThrow(() -> new IdNotFoundException("reservation"));
        return this.entityToResponse(reservationFromDB);
    }

    @Override
    public ReservationResponse update(ReservationRequest request, UUID id) {

        var hotel = this.hotelRepository.findById(request.getIdHotel()).orElseThrow(() -> new IdNotFoundException("hotel"));

        var reservationToUpdate = this.reservationRepository.findById(id).orElseThrow(() -> new IdNotFoundException("reservation"));

        reservationToUpdate.setHotel(hotel);
        reservationToUpdate.setTotalDays(request.getTotalDays());
        reservationToUpdate.setDateTimeReservation(LocalDateTime.now());
        reservationToUpdate.setDateStart(LocalDate.now());
        reservationToUpdate.setDateEnd(LocalDate.now().plusDays(request.getTotalDays()));
        reservationToUpdate.setPrice(hotel.getPrice().add(hotel.getPrice().multiply(charges_price_percentage)));

        var reservationUpdated = this.reservationRepository.save(reservationToUpdate);
        log.info("Reservation updated with id {}", reservationUpdated.getId());

        return this.entityToResponse(reservationUpdated);
    }

    @Override
    public void delete(UUID id) {
        var reservationToDelete = reservationRepository.findById(id).orElseThrow(() -> new IdNotFoundException("reservation"));
        this.reservationRepository.delete(reservationToDelete);
    }

    private ReservationResponse entityToResponse(ReservationEntity entity) {
        var response = new ReservationResponse();
        BeanUtils.copyProperties(entity, response);
        var hotelResponse = new HotelResponse();
        BeanUtils.copyProperties(entity.getHotel(), hotelResponse);
        response.setHotel(hotelResponse);
        return response;
    }

    @Override
    public BigDecimal findPrice(Long hotelId, Currency currency) {
        var hotel = this.hotelRepository.findById(hotelId).orElseThrow(() -> new IdNotFoundException("hotel"));
        var priceInDollars = hotel.getPrice().add(hotel.getPrice().multiply(charges_price_percentage));
        if (currency.equals(Currency.getInstance("USD"))) {
            return priceInDollars;
        }
        var currencyDto = this.currencyConnectorHelper.getCurrency(currency);
        log.info("API currency in {}, response: {}", currencyDto.getExchangeDate().toString(), currencyDto.getRates());

        return priceInDollars.multiply(currencyDto.getRates().get(currency));
    }

    public static final BigDecimal charges_price_percentage = BigDecimal.valueOf(0.20);

}
