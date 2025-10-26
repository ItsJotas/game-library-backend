package com.example.game_library_backend.model.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum GameStatusEnum {

    NOT_PLAYED("Not Played"),
    PLAYING("Playing"),
    PLAYED("Played"),
    FINISHED("Finished"),
    ONE_HUNDRED_PERCENT("100%");

    private final String description;
}