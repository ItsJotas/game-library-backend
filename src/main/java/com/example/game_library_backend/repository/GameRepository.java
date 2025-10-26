package com.example.game_library_backend.repository;

import com.example.game_library_backend.model.Game;
import com.example.game_library_backend.model.enums.GameStatusEnum;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface GameRepository extends JpaRepository<Game, Long> {

    Game findByName(String gameName);

    @Query(value = """
            SELECT g FROM Game g
            WHERE (:name IS NULL OR LOWER(g.name) LIKE LOWER(CONCAT('%', :name, '%')))
              AND (:launcher IS NULL OR LOWER(g.launcher) LIKE LOWER(CONCAT('%', :launcher, '%')))
              AND (:gameStatus IS NULL OR g.gameStatusEnum = :gameStatus)
            ORDER BY
              CASE WHEN :sortBy = 'name' AND :orderBy = 'asc' THEN g.name END ASC,
              CASE WHEN :sortBy = 'name' AND :orderBy = 'desc' THEN g.name END DESC,
              CASE WHEN :sortBy = 'launcher' AND :orderBy = 'asc' THEN g.launcher END ASC,
              CASE WHEN :sortBy = 'launcher' AND :orderBy = 'desc' THEN g.launcher END DESC
            """)
    Page<Game> findAllPaged(Pageable paging, @Param("name") String name, @Param("launcher") String launcher,
                            @Param("gameStatus") GameStatusEnum gameStatus, @Param("sortBy") String sortBy,
                            @Param("orderBy") String orderBy);
}