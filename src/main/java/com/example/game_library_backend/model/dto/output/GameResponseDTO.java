package com.example.game_library_backend.model.dto.output;

import com.example.game_library_backend.model.enums.GameStatusEnum;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class GameResponseDTO {

    private Long id;
    private String name;
    private String launcher;
    private GameStatusEnum gameStatusEnum;
    private LocalDate finishDate;
    private LocalDate oneHundredPercentDate;
    private MultipartFile image;

}