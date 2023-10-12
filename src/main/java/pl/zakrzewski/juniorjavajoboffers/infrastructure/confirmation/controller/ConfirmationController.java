package pl.zakrzewski.juniorjavajoboffers.infrastructure.confirmation.controller;

import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import pl.zakrzewski.juniorjavajoboffers.domain.confirmation.ConfirmationFacade;
import pl.zakrzewski.juniorjavajoboffers.domain.confirmation.dto.ConfirmationTokenResultDto;
import pl.zakrzewski.juniorjavajoboffers.domain.register.RegisterFacade;

@RestController
@RequestMapping("/api/v1")
@AllArgsConstructor
public class ConfirmationController {
    private final ConfirmationFacade confirmationFacade;
    private final RegisterFacade registerFacade;

    @GetMapping("/confirm")
    public ResponseEntity<ConfirmationTokenResultDto> confirmAccount(@RequestParam("token") String token) {
        ConfirmationTokenResultDto confirmationTokenResultDto = confirmationFacade.confirmToken(token);
        registerFacade.setUserEnabledByEmail(confirmationTokenResultDto.getEmail());
        return ResponseEntity.ok(confirmationTokenResultDto);
    }
}
