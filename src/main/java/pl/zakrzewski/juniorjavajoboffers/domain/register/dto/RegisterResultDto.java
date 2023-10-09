package pl.zakrzewski.juniorjavajoboffers.domain.register.dto;

import lombok.Builder;

@Builder
public record RegisterResultDto(String id, boolean created, String email, boolean enabled, String token){
}
