package com.pragma.fc.user_service.infraestructure.out.feign.adapter;

import com.pragma.fc.user_service.domain.spi.IRestaurantClientPort;
import com.pragma.fc.user_service.infraestructure.exception.RestaurantNotFoundException;
import com.pragma.fc.user_service.infraestructure.exception.WorkerAssignmentFailedException;
import com.pragma.fc.user_service.infraestructure.input.rest.dto.ApiSuccess;
import com.pragma.fc.user_service.infraestructure.input.security.entity.JwtAuthenticationToken;
import com.pragma.fc.user_service.infraestructure.out.feign.client.IRestaurantClientFeign;
import com.pragma.fc.user_service.infraestructure.out.feign.client.response.WorkerRestaurantResponseDto;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;


public class RestaurantFeignAdapter implements IRestaurantClientPort {
    private final IRestaurantClientFeign restaurantClientFeign;

    public RestaurantFeignAdapter(IRestaurantClientFeign restaurantClientFeign) {
        this.restaurantClientFeign = restaurantClientFeign;
    }

    @Override
    public void assignWorkerToRestaurant(Long restaurantNit, Long userDocumentNumber) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String token = null;

        if (authentication instanceof JwtAuthenticationToken jwtAuthenticationToken) {
            token = jwtAuthenticationToken.getToken();
        }

        ApiSuccess<WorkerRestaurantResponseDto> response = restaurantClientFeign.assignWorkerToRestaurant(restaurantNit, userDocumentNumber, "Bearer " + token);

        if (response.getPayload() == null
                || response.getPayload().getRestaurantNit() == null
                || response.getPayload().getWorkerDocumentNumber() == null
        ) {
            throw new WorkerAssignmentFailedException();
        }
    }

    @Override
    public Long getRestaurantNitByOwner() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String token = null;

        if (authentication instanceof JwtAuthenticationToken jwtAuthenticationToken) {
            token = jwtAuthenticationToken.getToken();
        }

        ApiSuccess<Long> response = restaurantClientFeign.getRestaurantNitByOwner("Bearer " + token);

        if (response == null || response.getPayload() == null) {
            throw new RestaurantNotFoundException();
        }

        return response.getPayload();
    }
}
