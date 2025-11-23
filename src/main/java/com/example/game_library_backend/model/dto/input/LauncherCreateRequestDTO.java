package com.example.game_library_backend.model.dto.input;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class LauncherCreateRequestDTO {

    @NotNull(message = "The field Name cannot be null.")
    @Size(max = 50, message = "The field Name cannot exceed 50 characters.")
    private String name;

    private MultipartFile icon;
}