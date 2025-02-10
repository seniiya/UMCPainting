package umc.pating.controllers;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import umc.pating.config.oauth.provider.JwtTokenProvider;

import java.util.List;

@RestController
@RequestMapping("/token")
@RequiredArgsConstructor
public class TokenController {

    private final JwtTokenProvider jwtTokenProvider;

    @GetMapping("/refresh")
    public ResponseEntity<?> refreshAuth(HttpServletRequest request, HttpServletResponse response) {
        String refreshToken = request.getHeader("Refresh");

        if (refreshToken != null && jwtTokenProvider.validateToken(refreshToken)) {
            String userEmail = jwtTokenProvider.getAuthentication(refreshToken).getName();
            String newAccessToken = jwtTokenProvider.createToken(userEmail, List.of("ROLE_USER"));

            response.addHeader("Authorization", "Bearer " + newAccessToken);
            return ResponseEntity.ok("NEW JWT ISSUED");
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("INVALID REFRESH TOKEN");
    }
}

