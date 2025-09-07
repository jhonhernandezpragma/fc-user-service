package com.pragma.fc.user_service.infraestructure.out.feign.client;

import com.pragma.fc.user_service.infraestructure.input.rest.dto.ApiSuccess;
import com.pragma.fc.user_service.infraestructure.out.feign.client.response.WorkerRestaurantResponseDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;

@FeignClient(name = "food-curt", url = "${food-curt.service.url}")
public interface IRestaurantClientFeign {
    @PostMapping("/{restaurantNit}/workers/{userDocumentNumber}")
    ApiSuccess<WorkerRestaurantResponseDto> assignWorkerToRestaurant(
            @PathVariable Long restaurantNit,
            @PathVariable Long userDocumentNumber,
            @RequestHeader("Authorization") String token
    );


    @GetMapping("/me")
    ApiSuccess<Long> getRestaurantNitByOwner(
            @RequestHeader("Authorization") String token
    );
}
