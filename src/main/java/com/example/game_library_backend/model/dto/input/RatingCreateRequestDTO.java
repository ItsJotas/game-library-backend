package com.example.game_library_backend.model.dto.input;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class RatingCreateRequestDTO {

    @NotNull(message = "The field Gameplay cannot be null.")
    private BigDecimal gameplay;

    @NotNull(message = "The field Graphics cannot be null.")
    private BigDecimal graphics;

    @NotNull(message = "The field Sound cannot be null.")
    private BigDecimal sound;

    @NotNull(message = "The field Story cannot be null.")
    private BigDecimal story;

    @NotNull(message = "The field Performance cannot be null.")
    private BigDecimal performance;
}