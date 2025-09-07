package com.pragma.fc.user_service.domain.exception;

public class OwnerRestaurantNotFoundException extends RuntimeException {
    public OwnerRestaurantNotFoundException(Long ownerDocumentNumber) {
        super("No restaurant was found for the owner with document: " + ownerDocumentNumber);
    }
}
