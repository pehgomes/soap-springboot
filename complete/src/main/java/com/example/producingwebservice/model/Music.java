package com.example.producingwebservice.model;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "music")
public class Music {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "music_id", nullable = false)
    private Long musicId;

    @Column(name = "name", nullable = false)
    private String name;

    @ManyToMany(mappedBy = "musics")
    private List<PlayList> playlists = new ArrayList<>();


    public Music() {}

    public Music(String name) {
        this.name = name;
    }

    public Long getMusicId() {
        return musicId;
    }

    public void setMusicId(Long musicId) {
        this.musicId = musicId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<PlayList> getPlaylists() {
        return playlists;
    }

    public void setPlaylists(List<PlayList> playlists) {
        this.playlists = playlists;
    }
}
