package com.pinuspintar.be.model;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class TokenRequest {

    @Id
    private Long id;
    private String merchantUserId;
    private String instruksi;
    private String status;

    // Getters and Setters
}
