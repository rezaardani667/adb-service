package com.pinuspintar.be.repository;

import com.pinuspintar.be.model.TransferRequest;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TransferRequestRepository extends JpaRepository<TransferRequest, Long> {
}
