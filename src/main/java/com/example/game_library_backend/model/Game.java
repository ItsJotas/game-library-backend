package com.example.game_library_backend.model;

import com.example.game_library_backend.model.enums.GameStatusEnum;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "game")
public class Game {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Long id;

    @Column(name = "NAME", nullable = false)
    private String name;

    @Column(name = "LAUNCHER", nullable = false)
    private String launcher;

    @Enumerated(EnumType.STRING)
    @Column(name = "GAME_STATUS_ENUM", nullable = false)
    private GameStatusEnum gameStatusEnum;

    @Column(name = "FINISH_DATE")
    private LocalDate finishDate;

    @Column(name = "ONE_HUNDRED_PERCENT_DATE")
    private LocalDate oneHundredPercentDate;

    @Column(name = "IMAGE_URL")
    private String imageUrl;

    @Column(name = "STATUS", nullable = false)
    private Boolean status;
}