package com.pinuspintar.be.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;
import com.pinuspintar.be.util.TokenRequestService;
import com.pinuspintar.be.model.TokenRequest;
import com.pinuspintar.be.DTO.UpdateStatusRequest;
import java.util.UUID;

@RestController
@RequestMapping("/api")
public class TokenRequestController {

    @Autowired
    private TokenRequestService tokenRequestService;

    @PostMapping("/token-request")
    public ResponseEntity<TokenRequest> createTokenRequest(@Valid @RequestBody TokenRequest tokenRequest) {
        TokenRequest savedTokenRequest = tokenRequestService.createTokenRequest(tokenRequest.getMerchantUserId(), tokenRequest.getInstruksi());
        return ResponseEntity.ok(savedTokenRequest);
    }

    @GetMapping("/{id}")
    public ResponseEntity<TokenRequest> getTokenRequest(@PathVariable UUID id) {
        TokenRequest tokenRequest = tokenRequestService.getTokenRequestById(id);
        return ResponseEntity.ok(tokenRequest);
    }

    @PutMapping("/token-request/{id}/status")
    public ResponseEntity<TokenRequest> updateStatus(@PathVariable UUID id, @RequestBody String status) {
        try {TokenRequest updatedTokenRequest = tokenRequestService.updateStatus(id, status);
            return ResponseEntity.ok(updatedTokenRequest);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }
}
