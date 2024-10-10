package com.pinuspintar.be.repository;

import com.pinuspintar.be.model.TokenRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface TokenRequestRepository extends JpaRepository<TokenRequest, UUID> {
    List<TokenRequest> findTokenRequestByStatus(String status);
}
