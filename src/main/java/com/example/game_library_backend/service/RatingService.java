package com.example.game_library_backend.service;

import com.example.game_library_backend.model.Game;
import com.example.game_library_backend.model.Rating;
import com.example.game_library_backend.model.dto.input.RatingCreateRequestDTO;
import com.example.game_library_backend.model.dto.input.RatingUpdateRequestDTO;
import com.example.game_library_backend.model.dto.output.RatingResponseDTO;
import com.example.game_library_backend.repository.RatingRepository;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;

import static com.example.game_library_backend.model.enums.AverageEnum.GAMEPLAY;
import static com.example.game_library_backend.model.enums.AverageEnum.GRAPHICS;
import static com.example.game_library_backend.model.enums.AverageEnum.PERFORMANCE;
import static com.example.game_library_backend.model.enums.AverageEnum.SOUND;
import static com.example.game_library_backend.model.enums.AverageEnum.STORY;

@Service
@RequiredArgsConstructor
public class RatingService {

    private final RatingRepository repository;
    private final GameService gameService;
    private final ModelMapper mapper;

    public void create(@Valid RatingCreateRequestDTO ratingCreateRequestDTO, Long gameId) {
        Game game = gameService.findById(gameId);

        Rating rating = mapper.map(ratingCreateRequestDTO, Rating.class);
        BigDecimal average = calculateAverage(rating);
        rating.setAverage(average);
        rating.setStatus(true);

        save(rating);
        game.setRating(rating);
        gameService.save(game);
    }

    private BigDecimal calculateAverage(Rating rating) {
        BigDecimal totalWeight = GAMEPLAY.getWeight()
                .add(PERFORMANCE.getWeight())
                .add(STORY.getWeight())
                .add(GRAPHICS.getWeight())
                .add(SOUND.getWeight());

        BigDecimal weightedSum = rating.getGameplay().multiply(GAMEPLAY.getWeight())
                .add(rating.getPerformance().multiply(PERFORMANCE.getWeight()))
                .add(rating.getStory().multiply(STORY.getWeight()))
                .add(rating.getGraphics().multiply(GRAPHICS.getWeight()))
                .add(rating.getSound().multiply(SOUND.getWeight()));

        return weightedSum.divide(totalWeight, 2, RoundingMode.HALF_UP);
    }

    private void save(Rating rating) {
        repository.save(rating);
    }

    public Rating findByGameId(Long gameId) {
        return repository.getByGameId(gameId);
    }

    public RatingResponseDTO getByGameId(Long gameId) {
        Rating rating = findByGameId(gameId);
        return mapper.map(rating, RatingResponseDTO.class);
    }

    public void update(RatingUpdateRequestDTO ratingUpdateRequestDTO, Long gameId) {
        Rating rating = findByGameId(gameId);
        mapper.map(ratingUpdateRequestDTO, rating);
        BigDecimal average = calculateAverage(rating);
        rating.setAverage(average);
        save(rating);
    }
}