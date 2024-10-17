package com.pinuspintar.be.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotBlank;
import java.util.UUID;
import lombok.Data;

@Entity
@Data
public class TokenRequest {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private UUID id;

	@NotBlank(message = "Merchant User ID cannot be blank.")
	private String merchantUserId;

	private String token;

	@NotBlank(message = "Instruksi cannot be blank.")
	private String instruksi;

	@NotBlank(message = "Status cannot be blank.")
	private String status;
}
