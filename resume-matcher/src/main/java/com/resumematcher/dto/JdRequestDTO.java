package com.resumematcher.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class JdRequestDTO {

    @NotBlank(message = "Role name is required")
    private String role;

    @NotBlank(message = "JD text is required")
    private String jdText;
}
