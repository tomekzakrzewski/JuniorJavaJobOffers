package pl.zakrzewski.juniorjavajoboffers.infrastructure.register.controller;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.zakrzewski.juniorjavajoboffers.domain.register.RegisterFacade;
import pl.zakrzewski.juniorjavajoboffers.domain.register.dto.ConfirmationTokenResultDto;
import pl.zakrzewski.juniorjavajoboffers.domain.register.dto.RegisterRequestDto;
import pl.zakrzewski.juniorjavajoboffers.domain.register.dto.RegistrationResultDto;

@RestController
@RequestMapping("/api/v1/registration")
@AllArgsConstructor
public class RegisterController {
    private final RegisterFacade registerFacade;

    @PostMapping
    public ResponseEntity<RegistrationResultDto> registerUser(@RequestBody RegisterRequestDto request) {
        RegistrationResultDto result = registerFacade.registerUser(request);
        return ResponseEntity.ok(result);
    }

    @GetMapping
    public ResponseEntity<ConfirmationTokenResultDto> confirmUserAccount (@RequestParam("token") String token) {
        ConfirmationTokenResultDto confirmationTokenResultDto = registerFacade.confirmToken(token);
        return ResponseEntity.ok(confirmationTokenResultDto);
    }

    @RequestMapping(value = "/unsubscribe", method = RequestMethod.DELETE)
    public ResponseEntity<Void> unsubscribeUserAccount(@RequestParam("id") String id) {
         registerFacade.deleteUserAndConfirmationToken(id);
         return ResponseEntity.noContent().build();
    }

}
