package com.example.game_library_backend.model.dto.input;

import com.example.game_library_backend.model.enums.GameStatusEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class GameUpdateRequestDTO {

    private String name;
    private Long launcherId;
    private GameStatusEnum gameStatusEnum;
    private LocalDate finishDate;
    private LocalDate oneHundredPercentDate;
    private MultipartFile image;

}