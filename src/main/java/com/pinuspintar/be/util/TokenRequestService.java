package com.pinuspintar.be.util;

import com.pinuspintar.be.model.TokenRequest;
import com.pinuspintar.be.repository.TokenRequestRepository;
import com.pinuspintar.be.util.TokenGenerator;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.UUID;

@Service
public class TokenRequestService {

    @Autowired
    private TokenRequestRepository tokenRequestRepository;

    @Transactional
    public TokenRequest createTokenRequest(String merchantUserId, String instruksi) {
        String token = TokenGenerator.generateToken();

        TokenRequest tokenRequest = new TokenRequest();
        tokenRequest.setMerchantUserId(merchantUserId);
        tokenRequest.setToken(token);
        tokenRequest.setStatus("pending");
        tokenRequest.setInstruksi(instruksi);

        return tokenRequestRepository.save(tokenRequest);
    }

    public TokenRequest insertTokenRequest(TokenRequest tokenRequest) {
        return tokenRequestRepository.save(tokenRequest);
    }

    public TokenRequest getTokenRequestById(UUID id) {
        return tokenRequestRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("TokenRequest not found"));
    }

    @Transactional
    public TokenRequest updateStatus(UUID id, String newStatus) {
        TokenRequest tokenRequest = getTokenRequestById(id);
            tokenRequest.setStatus(newStatus);
            return tokenRequestRepository.save(tokenRequest);
        }
}
