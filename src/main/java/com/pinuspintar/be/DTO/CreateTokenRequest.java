package com.pinuspintar.be.DTO;

import lombok.Data;

@Data
public class CreateTokenRequest {
    private String merchantUserId;
    private String amount;
    private String channel;
}
