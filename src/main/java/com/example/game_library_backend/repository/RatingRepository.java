package com.example.game_library_backend.repository;

import com.example.game_library_backend.model.Rating;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface RatingRepository extends JpaRepository<Rating, Long> {

    @Query(value = """
            SELECT r FROM Rating r
            JOIN Game g ON g.rating.id = r.id
            WHERE g.id = :gameId
                AND r.status = true
                AND g.status = true
            """)
    Rating getByGameId(@Param("gameId") Long gameId);
}