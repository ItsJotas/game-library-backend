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
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class GameService {

    private final GameRepository repository;
    private final ModelMapper mapper;

    @Value("${file.allowed-types}")
    private String allowedTypes;

    @Value("${file.upload-dir}")
    private String imagesDirectory;

    @Value("${image.base.url}")
    private String imageBaseUrl;

    public void create(@Valid GameCreateRequestDTO gameCreateRequestDTO) throws IOException {
        verifyIfNameExists(gameCreateRequestDTO.getName());

        validateDates(gameCreateRequestDTO);

        Game game = mapper.map(gameCreateRequestDTO, Game.class);
        game.setStatus(true);
        uploadImage(game, gameCreateRequestDTO.getImage());
        save(game);
    }

    public Page<GameResponseDTO> getAllGames(GameFilterDTO filterDTO, Integer pageNumber, Integer pageSize,
                                             String sortBy, String orderBy) {

        Pageable paging = PageRequest.of(pageNumber, pageSize);

        Page<Game> gamePaged = repository.findAllPaged(paging, filterDTO.getName(), filterDTO.getLauncher(),
                filterDTO.getGameStatusEnum(), sortBy, orderBy);
        return gamePaged.map(g -> mapper.map(g, GameResponseDTO.class));
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

    public void uploadImage(Game game, MultipartFile file) throws IOException {
        verifyImageExtension(file);

        String fileName = UUID.randomUUID() + "_" + file.getOriginalFilename();
        Path filePath = Paths.get(imagesDirectory, fileName);

        Files.createDirectories(filePath.getParent());

        Files.write(filePath, file.getBytes());

        String imageUrl = imageBaseUrl + fileName;
        game.setImageUrl(imageUrl);
    }

    private void verifyImageExtension(MultipartFile file) {
        String contentType = file.getContentType();
        List<String> allowedMimeTypes = Arrays.asList(allowedTypes.split(","));

        List<String> allowedExtensions = allowedMimeTypes.stream()
                .map(mimeType -> mimeType.substring(mimeType.indexOf("/") + 1))
                .map(ext -> "." + ext)
                .toList();

        if (!allowedMimeTypes.contains(contentType)) {
            throw new BadRequestException("Invalid file type. Allowed types are: " + String.join(", ",
                    allowedExtensions));
        }
    }

    private static void validateDates(GameCreateRequestDTO gameCreateRequestDTO) {
        if(Objects.nonNull(gameCreateRequestDTO.getFinishDate()) &&
                gameCreateRequestDTO.getFinishDate().isAfter(LocalDate.now())){
            throw new BadRequestException("The Finish Date cannot be in the future.");
        }

        if(Objects.nonNull(gameCreateRequestDTO.getOneHundredPercentDate()) &&
                gameCreateRequestDTO.getOneHundredPercentDate().isAfter(LocalDate.now())){
            throw new BadRequestException("The One Hundred Percent Date cannot be in the future.");
        }
    }
}