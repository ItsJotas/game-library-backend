package com.example.game_library_backend.model.dto.input;

import com.example.game_library_backend.model.enums.GameStatusEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class GameFilterDTO {

    private String name;
    private String launcher;
    private GameStatusEnum gameStatusEnum;
}