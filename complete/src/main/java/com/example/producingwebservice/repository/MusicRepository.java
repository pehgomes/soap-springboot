package com.example.producingwebservice.repository;

import com.example.producingwebservice.model.Music;
import com.example.producingwebservice.model.PlayList;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MusicRepository extends JpaRepository<Music, Long> {


    @Query("SELECT DISTINCT music FROM Music music " +
            "JOIN FETCH music.playlists playlists " +
            "WHERE music.musicId =:id")
    Optional<Music> findById(Long id);


}
