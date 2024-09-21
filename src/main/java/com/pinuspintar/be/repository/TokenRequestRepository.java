package com.pinuspintar.be.repository;

import com.pinuspintar.be.model.TokenRequest;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TokenRequestRepository extends JpaRepository<TokenRequest, Long> {
}
