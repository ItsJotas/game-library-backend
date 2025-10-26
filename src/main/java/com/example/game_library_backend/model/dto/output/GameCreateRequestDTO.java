package com.example.game_library_backend.model.dto.output;

import com.example.game_library_backend.model.enums.GameStatusEnum;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
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
public class GameCreateRequestDTO {

    @NotNull(message = "The field Name cannot be null.")
    @Size(max = 255, message = "The field Name cannot exceed 255 characters.")
    private String name;

    @NotNull(message = "The field Launcher cannot be null.")
    @Size(max = 255, message = "The field Launcher cannot exceed 255 characters.")
    private String launcher;

    @NotNull(message = "The field Game Status cannot be null.")
    private GameStatusEnum gameStatusEnum;

    private LocalDate finishDate;

    private LocalDate oneHundredPercentDate;

    private MultipartFile image;
}