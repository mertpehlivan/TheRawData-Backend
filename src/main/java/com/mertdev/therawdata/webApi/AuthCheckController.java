package com.mertdev.therawdata.webApi;

import java.util.UUID;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mertdev.therawdata.bussines.abstracts.UserService;
import com.mertdev.therawdata.dataAccess.abstracts.UserRepository;
import com.mertdev.therawdata.entities.concretes.User;

import lombok.AllArgsConstructor;

@RestController
@RequestMapping("/api/v1")
@AllArgsConstructor
public class AuthCheckController {
    private final UserService userService;
    private final UserRepository userRepository;

    @GetMapping("/check-auth")
    public ResponseEntity<UUID> checkAuthentication() {
        try {
            String email = userService.getCurrentUsername();
            User user = userRepository.findByEmail(email);
                   
            UUID userId = user.getId();

            return ResponseEntity.ok(userId);
        } catch (Exception e) {
            return ResponseEntity.status(500).body(null);
        }
    }
}
