package com.example.game_library_backend.controller;

import com.example.game_library_backend.model.dto.input.LauncherCreateRequestDTO;
import com.example.game_library_backend.model.dto.output.LauncherResponseDTO;
import com.example.game_library_backend.service.LauncherService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/launcher")
@RequiredArgsConstructor
public class LauncherController {

    private final LauncherService service;

    @PostMapping(consumes = "multipart/form-data")
    public ResponseEntity<Void> create(@ModelAttribute @Valid LauncherCreateRequestDTO launcherCreateRequestDTO)
            throws IOException {
        service.create(launcherCreateRequestDTO);
        return ResponseEntity.ok().build();
    }

    @GetMapping
    public ResponseEntity<List<LauncherResponseDTO>> getAllLaunchers() {
        List<LauncherResponseDTO> launcherDTOList = service.getAllLaunchers();
        return ResponseEntity.ok(launcherDTOList);
    }
}