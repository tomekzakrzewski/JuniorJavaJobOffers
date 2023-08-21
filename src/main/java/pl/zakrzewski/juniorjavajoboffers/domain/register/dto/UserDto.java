package pl.zakrzewski.juniorjavajoboffers.domain.register.dto;

import lombok.Builder;

@Builder
public record UserDto(String id, String username, String mail, boolean enabled) {
}