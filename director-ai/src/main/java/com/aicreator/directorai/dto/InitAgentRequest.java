package com.aicreator.directorai.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;

public record InitAgentRequest(
        @Valid @NotNull PersonaDto persona
) {}