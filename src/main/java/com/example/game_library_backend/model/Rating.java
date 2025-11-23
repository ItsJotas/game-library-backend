package com.example.game_library_backend.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "rating")
public class Rating {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Long id;

    @Column(name = "GAMEPLAY", nullable = false, scale = 2, precision = 4)
    private BigDecimal gameplay;

    @Column(name = "GRAPHICS", nullable = false, scale = 2, precision = 4)
    private BigDecimal graphics;

    @Column(name = "SOUND", nullable = false, scale = 2, precision = 4)
    private BigDecimal sound;

    @Column(name = "STORY", nullable = false, scale = 2, precision = 4)
    private BigDecimal story;

    @Column(name = "PERFORMANCE", nullable = false, scale = 2, precision = 4)
    private BigDecimal performance;

    @Column(name = "AVERAGE", nullable = false, scale = 2, precision = 4)
    private BigDecimal average;

    @Column(name = "STATUS")
    private Boolean status;
}