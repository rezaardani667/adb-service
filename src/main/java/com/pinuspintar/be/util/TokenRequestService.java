package com.pinuspintar.be.util;

import com.pinuspintar.be.enums.Status;
import com.pinuspintar.be.model.TokenRequest;
import com.pinuspintar.be.repository.TokenRequestRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class TokenRequestService {

	@Autowired
	private TokenRequestRepository tokenRequestRepository;

	// Metode untuk menyimpan data TokenRequest dengan token yang telah diekstrak
	public void saveTokenRequestData(String merchantUserId, String token, String instruksi, String status) {
		TokenRequest tokenRequest = new TokenRequest();
		tokenRequest.setMerchantUserId(merchantUserId);
		tokenRequest.setToken(token);
		tokenRequest.setInstruksi(instruksi);
		tokenRequest.setStatus(status);
		tokenRequestRepository.save(tokenRequest);
	}

	// Metode untuk memperbarui token pada TokenRequest yang ada
	public void updateToken(UUID id, String token) {
		TokenRequest tokenRequest = tokenRequestRepository.findById(id)
				.orElseThrow(() -> new RuntimeException("TokenRequest not found."));
		tokenRequest.setToken(token);
		tokenRequestRepository.save(tokenRequest);
	}

	// Membuat TokenRequest baru dengan status pending dan token auto-generated
	@Transactional
	public TokenRequest createTokenRequest(String merchantUserId, String instruksi) {
		String token = TokenGenerator.generateToken();

		TokenRequest tokenRequest = new TokenRequest();
		tokenRequest.setMerchantUserId(merchantUserId);
		tokenRequest.setToken(token);
		tokenRequest.setStatus(Status.pending.name());
		tokenRequest.setInstruksi(instruksi);

		return tokenRequestRepository.save(tokenRequest);
	}

	// Mendapatkan TokenRequest berdasarkan ID
	public TokenRequest getTokenRequestById(UUID id) {
		return tokenRequestRepository.findById(id).orElseThrow(() -> new RuntimeException("TokenRequest not found"));
	}

	// Memperbarui status TokenRequest
	@Transactional
	public TokenRequest updateStatus(UUID id, String newStatus) {
		TokenRequest tokenRequest = getTokenRequestById(id);
		tokenRequest.setStatus(newStatus);
		return tokenRequestRepository.save(tokenRequest);
	}
}