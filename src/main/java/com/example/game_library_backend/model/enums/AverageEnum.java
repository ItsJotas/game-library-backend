package com.example.game_library_backend.model.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.math.BigDecimal;

import static com.example.game_library_backend.utils.Constants.FIVE;
import static com.example.game_library_backend.utils.Constants.FOUR;
import static com.example.game_library_backend.utils.Constants.THREE;
import static com.example.game_library_backend.utils.Constants.TWO;

@Getter
@AllArgsConstructor
public enum AverageEnum {

    GAMEPLAY(FIVE),
    PERFORMANCE(FOUR),
    STORY(THREE),
    GRAPHICS(TWO),
    SOUND(TWO);

    private final BigDecimal weight;
}