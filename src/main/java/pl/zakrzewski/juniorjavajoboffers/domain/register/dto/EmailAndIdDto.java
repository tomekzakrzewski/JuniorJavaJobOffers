package pl.zakrzewski.juniorjavajoboffers.domain.register.dto;

import lombok.Builder;

@Builder
public record EmailAndIdDto(String email, String id) {
}
