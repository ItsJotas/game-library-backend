package com.example.game_library_backend.repository;

import com.example.game_library_backend.model.Game;
import com.example.game_library_backend.model.enums.GameStatusEnum;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface GameRepository extends JpaRepository<Game, Long> {

    @Query(value = "SELECT g FROM Game g WHERE g.name = :gameName AND g.status = true")
    Game findByName(@Param("gameName") String gameName);

    @Query(value = """
            SELECT g FROM Game g
            JOIN Launcher l ON l.id = g.launcher.id
            WHERE (:name IS NULL OR LOWER(g.name) LIKE LOWER(CONCAT('%', :name, '%')))
              AND (:launcherName IS NULL OR LOWER(l.name) LIKE LOWER(CONCAT('%', :launcherName, '%')))
              AND (:gameStatus IS NULL OR g.gameStatusEnum = :gameStatus)
              AND g.status = true
            ORDER BY
              CASE WHEN :sortBy = 'name' AND :orderBy = 'asc' THEN g.name END ASC,
              CASE WHEN :sortBy = 'name' AND :orderBy = 'desc' THEN g.name END DESC,
              CASE WHEN :sortBy = 'launcherName' AND :orderBy = 'asc' THEN l.name END ASC,
              CASE WHEN :sortBy = 'launcherName' AND :orderBy = 'desc' THEN l.name END DESC
            """)
    List<Game> findAll(@Param("name") String name, @Param("launcherName") String launcherName,
                       @Param("gameStatus") GameStatusEnum gameStatus, @Param("sortBy") String sortBy,
                       @Param("orderBy") String orderBy);

    @Query(value = "SELECT count(g.id) FROM Game g WHERE g.status = true")
    Integer getTotalGamesNumber();
}