package pl.zakrzewski.juniorjavajoboffers.domain.confirmation.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@AllArgsConstructor
@Getter
@Setter
public class ConfirmationTokenResultDto {
    private String token;
    private LocalDateTime confirmedAt;
}
