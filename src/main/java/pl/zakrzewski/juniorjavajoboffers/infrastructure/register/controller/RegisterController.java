package pl.zakrzewski.juniorjavajoboffers.infrastructure.register.controller;

import lombok.AllArgsConstructor;
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
    public RegistrationResultDto register(@RequestBody RegisterRequestDto request) {
        return registerFacade.registerUser(request);
    }

    @GetMapping(path = "confirm")
    public ConfirmationTokenResultDto confirm (@RequestParam("token") String token) {
        return registerFacade.confirmToken(token);
    }

}
