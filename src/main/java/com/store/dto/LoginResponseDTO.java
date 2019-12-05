package com.store.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.util.UUID;

@AllArgsConstructor
@Builder
@Data public class LoginResponseDTO {

    private String accessToken;
    private UUID id;
    private String name;
    private String email;
    private final String tokenType = "Bearer";

}
