package pl.zakrzewski.juniorjavajoboffers.domain.confirmation;

import pl.zakrzewski.juniorjavajoboffers.domain.confirmation.dto.ConfirmationTokenDto;

public class ConfirmationTokenMapper {

    public static ConfirmationTokenDto mapConfirmationTokenToConfirmationTokenDto(ConfirmationToken confirmationToken) {
        return ConfirmationTokenDto.builder()
                .id(confirmationToken.getId())
                .token(confirmationToken.getToken())
                .createdAt(confirmationToken.getCreatedAt())
                .expiresAt(confirmationToken.getExpiresAt())
                .confirmedAt(confirmationToken.getConfirmedAt())
                .build();
    }
}
