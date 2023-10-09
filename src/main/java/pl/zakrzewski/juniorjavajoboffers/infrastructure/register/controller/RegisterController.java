package pl.zakrzewski.juniorjavajoboffers.infrastructure.register.controller;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.zakrzewski.juniorjavajoboffers.domain.register.RegisterFacade;
import pl.zakrzewski.juniorjavajoboffers.domain.register.dto.ConfirmationTokenResultDto;
import pl.zakrzewski.juniorjavajoboffers.domain.register.dto.RegisterRequestDto;
import pl.zakrzewski.juniorjavajoboffers.domain.register.dto.RegisterResultDto;

@RestController
@RequestMapping("/api/v1/registration")
@AllArgsConstructor
public class RegisterController {
    private final RegisterFacade registerFacade;

    @PostMapping
    public ResponseEntity<RegisterResultDto> registerUser(@RequestBody RegisterRequestDto request) {
        RegisterResultDto result = registerFacade.registerUser(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(result);
    }

    @GetMapping
    public ResponseEntity<ConfirmationTokenResultDto> confirmUserAccount (@RequestParam("token") String token) {
        ConfirmationTokenResultDto confirmationTokenResultDto = registerFacade.confirmToken(token);
        return ResponseEntity.ok(confirmationTokenResultDto);
    }

    @PostMapping("/unsubscribe")
    public ResponseEntity<Void> unsubscribeUserAccount(@RequestParam("id") String id) {
         registerFacade.deleteUserAndConfirmationToken(id);
         return ResponseEntity.noContent().build();
    }
}
