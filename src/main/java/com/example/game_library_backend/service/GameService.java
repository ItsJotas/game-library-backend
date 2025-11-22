package com.example.game_library_backend.service;

import com.example.game_library_backend.exception.customized.BadRequestException;
import com.example.game_library_backend.exception.customized.ObjectNotFoundException;
import com.example.game_library_backend.model.Game;
import com.example.game_library_backend.model.dto.input.GameCreateRequestDTO;
import com.example.game_library_backend.model.dto.input.GameFilterDTO;
import com.example.game_library_backend.model.dto.input.GameUpdateRequestDTO;
import com.example.game_library_backend.model.dto.output.GameResponseDTO;
import com.example.game_library_backend.repository.GameRepository;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class GameService {

    private final UploadImageService uploadImageService;
    private final GameRepository repository;
    private final ModelMapper mapper;

    @Transactional
    public void create(@Valid GameCreateRequestDTO gameCreateRequestDTO) throws IOException {
        verifyIfNameExists(gameCreateRequestDTO.getName(), null);

        validateDates(gameCreateRequestDTO.getFinishDate(), gameCreateRequestDTO.getOneHundredPercentDate());

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

    private void verifyIfNameExists(String gameName, Game updatedGame) {
        Game game = repository.findByName(gameName);
        boolean hasName = Objects.isNull(updatedGame)
                ? Objects.nonNull(game)
                : !Objects.equals(game.getId(), updatedGame.getId());

        if(hasName){
            throw new BadRequestException("There is already a Game with this name: " + gameName);
        }
    }

    private static void validateDates(LocalDate finishDate, LocalDate oneHundredPercentDate) {
        if(Objects.nonNull(finishDate) && finishDate.isAfter(LocalDate.now())){
            throw new BadRequestException("The Finish Date cannot be in the future");
        }

        if(Objects.nonNull(oneHundredPercentDate) && oneHundredPercentDate.isAfter(LocalDate.now())){
            throw new BadRequestException("The One Hundred Percent Date cannot be in the future");
        }
    }

    public Integer getTotalGamesNumber() {
        return repository.getTotalGamesNumber();
    }

    @Transactional
    public void update(@Valid GameUpdateRequestDTO gameUpdateRequestDTO, Long gameId) throws IOException {
        validateDates(gameUpdateRequestDTO.getFinishDate(), gameUpdateRequestDTO.getOneHundredPercentDate());

        Game game = findById(gameId);
        verifyIfNameExists(gameUpdateRequestDTO.getName(), game);
        mapper.map(gameUpdateRequestDTO, game);

        if (Objects.nonNull(gameUpdateRequestDTO.getImage()) && !gameUpdateRequestDTO.getImage().isEmpty()) {
            String imageId = game.getImageUrl().split("id=")[1].split("&sz")[0];
            uploadImageService.removeImageFromDrive(imageId);
            uploadImageService.uploadImage(gameUpdateRequestDTO.getImage(), game);
        }

        save(game);
    }

    private Game findById(Long gameId) {
        return repository.findById(gameId)
                .orElseThrow(() -> new ObjectNotFoundException("Game not found with id: " + gameId));
    }

    public GameResponseDTO getGameById(Long gameId) {
        Game game = findById(gameId);
        return mapper.map(game, GameResponseDTO.class);
    }
}