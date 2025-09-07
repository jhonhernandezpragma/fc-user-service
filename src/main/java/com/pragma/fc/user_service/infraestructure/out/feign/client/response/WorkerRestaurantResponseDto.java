package com.pragma.fc.user_service.infraestructure.out.feign.client.response;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class WorkerRestaurantResponseDto {
    private Long workerDocumentNumber;
    private Long restaurantNit;

}
