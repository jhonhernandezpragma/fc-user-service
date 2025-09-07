package com.pragma.fc.user_service.infraestructure.exception;

public class WorkerAssignmentFailedException extends RuntimeException {
    public WorkerAssignmentFailedException() {
        super("Could not assign worker with document");
    }
}
