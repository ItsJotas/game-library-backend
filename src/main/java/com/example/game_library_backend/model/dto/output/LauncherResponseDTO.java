package com.example.game_library_backend.model.dto.output;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class LauncherResponseDTO {

    private Long id;
    private String name;
    private String iconUrl;
}