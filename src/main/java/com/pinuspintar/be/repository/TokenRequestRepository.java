package com.pinuspintar.be.repository;

import com.pinuspintar.be.model.TokenRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TokenRequestRepository extends JpaRepository<TokenRequest, Long> {
}
