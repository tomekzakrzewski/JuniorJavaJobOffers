package pl.zakrzewski.juniorjavajoboffers.domain.register.dto;

import lombok.Builder;

@Builder
public record UserIdEmailDto(String email, String id) {
}
