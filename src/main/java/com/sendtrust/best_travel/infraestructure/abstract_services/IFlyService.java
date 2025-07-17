package com.sendtrust.best_travel.infraestructure.abstract_services;

import com.sendtrust.best_travel.api.models.responses.FlyResponse;

import java.util.Set;

public interface IFlyService extends CatalogService<FlyResponse> {

    Set<FlyResponse> readByOriginDestiny(String origin, String destiny);
}
