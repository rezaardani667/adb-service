package com.pinuspintar.be.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.pinuspintar.be.util.TokenRequestService;
import com.pinuspintar.be.model.TokenRequest;

@RestController
@RequestMapping("/api")
public class TokenRequestController {

    @Autowired
    private TokenRequestService tokenRequestService;

    @PostMapping("/token-request")
    public ResponseEntity<TokenRequest> createTokenRequest(@RequestBody TokenRequest tokenRequest) {
        tokenRequestService.createTokenRequest(tokenRequest.getMerchantUserId(), tokenRequest.getInstruksi());
        return ResponseEntity.ok(tokenRequest);
    }

    @GetMapping("/{id}")
    public ResponseEntity<TokenRequest> getTokenRequest(@PathVariable Long id) {
        TokenRequest tokenRequest = tokenRequestService.getTokenRequestById(id);
        return ResponseEntity.ok(tokenRequest);
    }

    // Tambahkan endpoint lain sesuai kebutuhan (misalnya update status atau delete)
}
