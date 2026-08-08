package com.aicreator.directorai.dto;

import jakarta.validation.constraints.NotBlank;

public record PersonaDto(
        @NotBlank String name,
        @NotBlank String domain
) {}