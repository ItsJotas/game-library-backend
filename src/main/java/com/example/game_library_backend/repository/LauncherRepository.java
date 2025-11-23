package com.example.game_library_backend.repository;

import com.example.game_library_backend.model.Launcher;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LauncherRepository extends JpaRepository<Launcher, Long> {

    @Query(value = "SELECT l FROM Launcher l WHERE l.name = :launcherName AND l.status = true")
    Launcher findByName(@Param("launcherName") String launcherName);

    @Query(value = "SELECT l FROM Launcher l WHERE l.status = true")
    List<Launcher> findAllLaunchers();

}