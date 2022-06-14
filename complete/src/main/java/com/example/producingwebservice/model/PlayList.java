package com.example.producingwebservice.model;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "playlist")
public class PlayList {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "playlist_id", nullable = false)
    private Long playListId;

    @Column(name = "name", nullable = false)
    private String name;

    @ManyToMany(cascade = {CascadeType.ALL})
    @JoinTable(
            name = "music_playlist",
            joinColumns = {@JoinColumn(name = "playlist_id")},
            inverseJoinColumns = {@JoinColumn(name = "music_id")}
    )
    private List<Music> musics = new ArrayList<>();

    @ManyToMany(mappedBy = "playlists")
    private List<User> users = new ArrayList<>();

    public PlayList() {
    }

    public PlayList(Long id) {
        this.playListId = id;
    }

    public PlayList(String name) {
        this.name = name;
    }

    public Long getPlayListId() {
        return playListId;
    }

    public void setPlayListId(Long playListId) {
        this.playListId = playListId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Music> getMusics() {
        return musics;
    }

    public void setMusics(List<Music> musics) {
        this.musics = musics;
    }

    public void addMusic(Music music) {
        this.musics.add(music);
    }

    public List<User> getUsers() {
        return users;
    }

    public void setUsers(List<User> users) {
        this.users = users;
    }
}
