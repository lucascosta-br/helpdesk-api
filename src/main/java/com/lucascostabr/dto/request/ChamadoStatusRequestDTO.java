package com.lucascostabr.dto.request;

import com.lucascostabr.enums.Status;
import jakarta.validation.constraints.NotNull;

public record ChamadoStatusRequestDTO(
        @NotNull(message = "O status é obrigatório")
        Status status
) {
}
