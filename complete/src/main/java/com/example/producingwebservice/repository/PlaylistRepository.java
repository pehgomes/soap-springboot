package com.example.producingwebservice.repository;

import com.example.producingwebservice.model.PlayList;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PlaylistRepository extends JpaRepository<PlayList, Long> {


    @Query("SELECT DISTINCT playlist FROM PlayList playlist " +
            "JOIN FETCH playlist.musics musics " +
            "WHERE playlist.playListId =:id")
    Optional<PlayList> findById(Long id);

}
