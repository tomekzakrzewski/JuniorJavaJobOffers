package pl.zakrzewski.juniorjavajoboffers.infrastructure.register.controller.error;

import org.springframework.http.HttpStatus;

public record RegisterErrorResponse(String message, HttpStatus status) {
}
