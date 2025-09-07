package com.pragma.fc.user_service.domain.spi;

public interface IRestaurantClientPort {
    void assignWorkerToRestaurant(Long restaurantNit, Long userDocumentNumber);

    Long getRestaurantNitByOwner();
}
