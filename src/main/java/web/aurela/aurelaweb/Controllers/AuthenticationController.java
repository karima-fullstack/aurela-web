package web.aurela.aurelaweb.Controllers;

import jakarta.mail.MessagingException;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;
import web.aurela.aurelaweb.Dtos.AuthenticationDto;
import web.aurela.aurelaweb.Dtos.AuthenticationResponseDto;
import web.aurela.aurelaweb.Dtos.RegistrationDto;
import web.aurela.aurelaweb.Dtos.UserDto;
import web.aurela.aurelaweb.Entities.User;
import web.aurela.aurelaweb.Repositories.UserRepository;
import web.aurela.aurelaweb.Services.AuthenticationService;

import java.util.Optional;


@RestController
@RequestMapping("/aurela/api/auth")
@RequiredArgsConstructor
public class AuthenticationController {

    private final AuthenticationService authenticationservice;
    private final UserRepository userRepository;

    @GetMapping("/me")
    public ResponseEntity<UserDto> getCurrentUser(@AuthenticationPrincipal UserDetails userDetails) {
        if (userDetails == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        User user = userRepository.findByEmail(userDetails.getUsername())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.UNAUTHORIZED, "User not found"));

        UserDto dto = UserDto.builder()
                .id(user.getId())
                .email(user.getEmail())
                .name(user.getName())
                .build();

        return ResponseEntity.ok(dto);
    }

    @PostMapping("/register")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public ResponseEntity<?> register(
            @RequestBody @Valid RegistrationDto request
    ) throws MessagingException {
        authenticationservice.register(request);
        return ResponseEntity.accepted().build();
    }

    @PostMapping("/authenticate")
    public ResponseEntity<?> authenticate(
            @RequestBody AuthenticationDto request,
            HttpServletResponse response
    ) {
        AuthenticationResponseDto authResponse = authenticationservice.authenticate(request);

        // Create HttpOnly cookie with JWT
        ResponseCookie cookie = ResponseCookie.from("jwt", authResponse.getToken())
                .httpOnly(true)
                .secure(true)
                .path("/")
                .maxAge(24 * 60 * 60) // 1 day expiration
                .sameSite("Lax")
                .build();

        response.setHeader("Set-Cookie", cookie.toString());

        return ResponseEntity.ok().build();
    }

    @GetMapping("/activate-account")
    public void confirm(
            @RequestParam String token
    ) throws MessagingException {
        authenticationservice.activateAccount(token);
    }

    @PostMapping("/logout")
    public ResponseEntity<?> logout(HttpServletResponse response) {
        ResponseCookie cookie = ResponseCookie.from("jwt", "")
                .httpOnly(true)
                .secure(true)
                .path("/")
                .maxAge(0) // Deletes the cookie
                .sameSite("Strict")
                .build();
        response.setHeader("Set-Cookie", cookie.toString());
        return ResponseEntity.ok().build();
    }


}
