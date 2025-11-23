package com.example.game_library_backend.service;

import com.example.game_library_backend.exception.customized.BadRequestException;
import com.example.game_library_backend.exception.customized.ObjectNotFoundException;
import com.example.game_library_backend.model.Launcher;
import com.example.game_library_backend.model.dto.input.LauncherCreateRequestDTO;
import com.example.game_library_backend.model.dto.output.LauncherResponseDTO;
import com.example.game_library_backend.repository.LauncherRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class LauncherService {

    private final LauncherRepository repository;
    private final UploadImageService uploadImageService;
    private final ModelMapper mapper;

    public void create(LauncherCreateRequestDTO launcherCreateRequestDTO) throws IOException {
        verifyIfNameExists(launcherCreateRequestDTO.getName());

        Launcher launcher = mapper.map(launcherCreateRequestDTO, Launcher.class);
        String iconUrl = uploadImageService.uploadImage(launcherCreateRequestDTO.getIcon());
        launcher.setIconUrl(iconUrl);
        launcher.setStatus(true);
        save(launcher);
    }

    private void verifyIfNameExists(String launcherName) {
        Launcher launcher = repository.findByName(launcherName);
        if(Objects.nonNull(launcher)){
            throw new BadRequestException("There is already a Game with this name: " + launcherName);
        }
    }

    public void save(Launcher launcher) {
        repository.save(launcher);
    }

    public List<LauncherResponseDTO> getAllLaunchers() {
        List<Launcher> launchers = repository.findAllLaunchers();
        return launchers.stream().map(launcher -> mapper.map(launcher, LauncherResponseDTO.class)).toList();
    }

    public Launcher findById(Long launcherId) {
        return repository.findById(launcherId)
                .orElseThrow(() -> new ObjectNotFoundException("Launcher not found with id: " + launcherId));
    }
}