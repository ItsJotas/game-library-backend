package com.example.game_library_backend.model.dto.output;

import com.example.game_library_backend.model.enums.GameStatusEnum;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class GameResponseDTO {

    private Long id;
    private String name;
    private LauncherResponseDTO launcher;
    private GameStatusEnum gameStatusEnum;
    private LocalDate finishDate;
    private LocalDate oneHundredPercentDate;
    private String imageUrl;

}