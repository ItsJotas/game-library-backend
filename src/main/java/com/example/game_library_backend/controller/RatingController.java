package com.example.game_library_backend.controller;

import com.example.game_library_backend.model.dto.input.RatingCreateRequestDTO;
import com.example.game_library_backend.model.dto.input.RatingUpdateRequestDTO;
import com.example.game_library_backend.model.dto.output.RatingResponseDTO;
import com.example.game_library_backend.service.RatingService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
@RequestMapping("/rating")
@RequiredArgsConstructor
public class RatingController {

    private final RatingService service;

    @PostMapping
    public ResponseEntity<Void> create(@RequestBody @Valid RatingCreateRequestDTO ratingCreateRequestDTO,
                                       @RequestParam Long gameId) throws IOException {
        service.create(ratingCreateRequestDTO, gameId);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/{gameId}")
    public ResponseEntity<RatingResponseDTO> getByGameId(@PathVariable Long gameId) throws IOException {
        RatingResponseDTO ratingDTO = service.getByGameId(gameId);
        return ResponseEntity.ok(ratingDTO);
    }

    @PutMapping("/{gameId}")
    public ResponseEntity<Void> update(@RequestBody RatingUpdateRequestDTO ratingUpdateRequestDTO,
                                       @PathVariable Long gameId) throws IOException {
        service.update(ratingUpdateRequestDTO, gameId);
        return ResponseEntity.ok().build();
    }
}