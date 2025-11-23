package com.example.game_library_backend.model.dto.input;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class RatingUpdateRequestDTO {

    private BigDecimal gameplay;
    private BigDecimal graphics;
    private BigDecimal sound;
    private BigDecimal story;
    private BigDecimal performance;

}