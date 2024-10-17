package com.pinuspintar.be.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Entity
@Data
public class TransferRequest {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotBlank(message = "Merchant User ID can not be blank.")
    private String merchantUserId;

    @NotBlank(message = "Amount can not be blank.")
    private String amount;

    @NotBlank(message = "Channel can not be blank.")
    private String channel;
}
