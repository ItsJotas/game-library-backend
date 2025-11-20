package com.example.game_library_backend.service;

import com.example.game_library_backend.exception.customized.BadRequestException;
import com.example.game_library_backend.model.Game;
import com.example.game_library_backend.model.dto.input.GameCreateRequestDTO;
import com.example.game_library_backend.model.dto.input.GameFilterDTO;
import com.example.game_library_backend.model.dto.output.GameResponseDTO;
import com.example.game_library_backend.repository.GameRepository;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.time.LocalDate;
import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class GameService {

    private final UploadImageService uploadImageService;
    private final GameRepository repository;
    private final ModelMapper mapper;

    public void create(@Valid GameCreateRequestDTO gameCreateRequestDTO) throws IOException, GeneralSecurityException {
        verifyIfNameExists(gameCreateRequestDTO.getName());

        validateDates(gameCreateRequestDTO);

        Game game = mapper.map(gameCreateRequestDTO, Game.class);
        game.setStatus(true);
        uploadImageService.uploadImage(gameCreateRequestDTO.getImage(), game);
        save(game);
    }

    public List<GameResponseDTO> getAllGames(GameFilterDTO filterDTO, String sortBy, String orderBy) {
        List<Game> games = repository.findAllPaged(filterDTO.getName(), filterDTO.getLauncher(),
                filterDTO.getGameStatusEnum(), sortBy, orderBy);

        return games.stream().map(g -> mapper.map(g, GameResponseDTO.class)).toList();
    }

    public void save(Game game) {
        repository.save(game);
    }

    private void verifyIfNameExists(String gameName) {
        Game game = repository.findByName(gameName);
        if(Objects.nonNull(game)){
            throw new BadRequestException("There is already a Game with this name: " + gameName);
        }
    }

    private static void validateDates(GameCreateRequestDTO gameCreateRequestDTO) {
        if(Objects.nonNull(gameCreateRequestDTO.getFinishDate()) &&
                gameCreateRequestDTO.getFinishDate().isAfter(LocalDate.now())){
            throw new BadRequestException("The Finish Date cannot be in the future");
        }

        if(Objects.nonNull(gameCreateRequestDTO.getOneHundredPercentDate()) &&
                gameCreateRequestDTO.getOneHundredPercentDate().isAfter(LocalDate.now())){
            throw new BadRequestException("The One Hundred Percent Date cannot be in the future");
        }
    }

    public Integer getTotalGamesNumber() {
        return repository.getTotalGamesNumber();
    }
}