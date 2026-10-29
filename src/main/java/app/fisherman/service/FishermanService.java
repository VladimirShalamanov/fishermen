package app.fisherman.service;

import app.exception.CustomException;
import app.fisherman.enums.Experience;
import app.fisherman.model.Fisherman;
import app.fisherman.repository.FishermanRepository;
import app.web.dto.EditProfileRequest;
import app.web.dto.LoginRequest;
import app.web.dto.RegisterRequest;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
public class FishermanService {

    private final FishermanRepository fishermanRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public FishermanService(FishermanRepository fishermanRepository,
                            PasswordEncoder passwordEncoder) {

        this.fishermanRepository = fishermanRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public int getCashByExperience(Experience experience) {

        if (experience == Experience.BEGINNER) {
            return 100;
        } else if (experience == Experience.INTERMEDIATE) {
            return 200;
        } else if (experience == Experience.EXPERT) {
            return 300;
        }
        return 0;
    }

    public Fisherman getById(UUID fishermanId) {

        return fishermanRepository.findById(fishermanId)
                .orElseThrow(() -> new CustomException("Fisherman with that id not found"));
    }

    public void register(RegisterRequest registerRequest) {

        Fisherman fisherman = Fisherman.builder()
                .username(registerRequest.getUsername())
                .password(passwordEncoder.encode(registerRequest.getPassword()))
                .nickname(registerRequest.getNickname())
                .experience(registerRequest.getExperience())
                .cash(getCashByExperience(registerRequest.getExperience()))
                .createdOn(LocalDateTime.now())
                .updatedOn(LocalDateTime.now())
                .build();

        fishermanRepository.save(fisherman);
    }

    public Fisherman login(LoginRequest loginRequest) {

        Fisherman fisherman = fishermanRepository.findByUsername(loginRequest.getUsername())
                .orElseThrow(() -> new CustomException("Username or password incorrect!"));

        if (!passwordEncoder.matches(loginRequest.getPassword(), fisherman.getPassword())) {
            throw new CustomException("Username or password incorrect!");
        }

        return fisherman;
    }

    public void updateProfile(UUID id, EditProfileRequest editProfileRequest) {

        Fisherman fisherman = getById(id);
        fisherman.setUsername(editProfileRequest.getUsername());
        fisherman.setNickname(editProfileRequest.getNickname());
        fisherman.setPassword(passwordEncoder.encode(editProfileRequest.getPassword()));
        fisherman.setImageUrl(editProfileRequest.getImageUrl());

        fisherman.setUpdatedOn(LocalDateTime.now());
        fishermanRepository.save(fisherman);
    }
}
