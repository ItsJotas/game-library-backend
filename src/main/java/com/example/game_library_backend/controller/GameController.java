package com.example.game_library_backend.controller;

import com.example.game_library_backend.model.dto.input.GameCreateRequestDTO;
import com.example.game_library_backend.model.dto.input.GameFilterDTO;
import com.example.game_library_backend.model.dto.output.GameResponseDTO;
import com.example.game_library_backend.service.GameService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.List;

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

    @PostMapping("/find-all")
    public ResponseEntity<List<GameResponseDTO>> getAllGames(@RequestBody @Valid GameFilterDTO filterDTO,
                                                             @RequestParam(defaultValue = "id") String sortBy,
                                                             @RequestParam(defaultValue = "asc") String orderBy) {
        List<GameResponseDTO> responseDTOPage = service.getAllGames(filterDTO, sortBy, orderBy);
        return ResponseEntity.ok(responseDTOPage);
    }

    @GetMapping("/total-games")
    public ResponseEntity<Integer> getTotalGamesNumber() {
        Integer totalGamesNumber = service.getTotalGamesNumber();
        return ResponseEntity.ok(totalGamesNumber);
    }
}