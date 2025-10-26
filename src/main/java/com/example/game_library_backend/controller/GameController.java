package com.example.game_library_backend.controller;

import com.example.game_library_backend.model.dto.output.GameCreateRequestDTO;
import com.example.game_library_backend.service.GameService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
@RequestMapping("/game")
@RequiredArgsConstructor
public class GameController {

    private final GameService service;

    @PostMapping(consumes = "multipart/form-data")
    public ResponseEntity<Void> create(@ModelAttribute @Valid GameCreateRequestDTO gameCreateRequestDTO) throws IOException {
        service.create(gameCreateRequestDTO);
        return ResponseEntity.ok().build();
    }
}