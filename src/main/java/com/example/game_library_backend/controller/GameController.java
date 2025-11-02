package com.example.game_library_backend.controller;

import com.example.game_library_backend.model.dto.input.GameCreateRequestDTO;
import com.example.game_library_backend.model.dto.input.GameFilterDTO;
import com.example.game_library_backend.model.dto.output.GameResponseDTO;
import com.example.game_library_backend.service.GameService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
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

    @PostMapping("/find-all")
    public ResponseEntity<Page<GameResponseDTO>> getAllGames(@RequestBody @Valid GameFilterDTO filterDTO,
                                                             @RequestParam(defaultValue = "0") Integer pageNumber,
                                                             @RequestParam(defaultValue = "10") Integer pageSize,
                                                             @RequestParam(defaultValue = "id") String sortBy,
                                                             @RequestParam(defaultValue = "asc") String orderBy) {
        Page<GameResponseDTO> responseDTOPage = service.getAllGames(filterDTO, pageNumber, pageSize, sortBy, orderBy);
        return ResponseEntity.ok(responseDTOPage);
    }

    @GetMapping("/total-games")
    public ResponseEntity<Integer> getTotalGamesNumber() {
        Integer totalGamesNumber = service.getTotalGamesNumber();
        return ResponseEntity.ok(totalGamesNumber);
    }
}