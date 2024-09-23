package com.pinuspintar.be.util;

import com.pinuspintar.be.model.TokenRequest;
import com.pinuspintar.be.repository.TokenRequestRepository;
import com.pinuspintar.be.util.TokenGenerator;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TokenRequestService {

    @Autowired
    private TokenRequestRepository tokenRequestRepository;

    @Transactional
    public void createTokenRequest(String merchantUserId, String instruksi) {
        // Generate token
        String token = TokenGenerator.generateToken();

        // Buat entitas TokenRequest
        TokenRequest tokenRequest = new TokenRequest();
        tokenRequest.setMerchantUserId(merchantUserId);
        tokenRequest.setToken(token);
        tokenRequest.setStatus("pending");
        tokenRequest.setInstruksi(instruksi);

        // Simpan ke database
        tokenRequestRepository.save(tokenRequest);
    }
}
