package com.example.producingwebservice;

import com.example.producingwebservice.model.Music;
import com.example.producingwebservice.model.PlayList;
import com.example.producingwebservice.model.User;
import com.example.producingwebservice.repository.MusicRepository;
import com.example.producingwebservice.repository.PlaylistRepository;
import com.example.producingwebservice.repository.UserRepository;
import io.spring.guides.gs_producing_web_service.GetMusicRequest;
import io.spring.guides.gs_producing_web_service.GetPlaylistRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Component
public class GeneralStreamService {


    @Autowired
    private MusicRepository musicRepository;

    @Autowired
    private PlaylistRepository playlistRepository;

    @Autowired
    private UserRepository userRepository;

    public List<io.spring.guides.gs_producing_web_service.Music> getMusicsByPlaylist(GetPlaylistRequest request) {

        PlayList play = playlistRepository.findById(new Integer(request.getId()).longValue()).get();

        List<io.spring.guides.gs_producing_web_service.Music> musicas = new ArrayList<>();

        for (Music music : play.getMusics()) {
            io.spring.guides.gs_producing_web_service.Music musica = new io.spring.guides.gs_producing_web_service.Music();

            musica.setIdMusic(music.getMusicId().intValue());
            musica.setName(music.getName());

            musicas.add(musica);
        }

        return musicas;
    }

    public List<io.spring.guides.gs_producing_web_service.Playlist> getPlaylistByMusic(GetMusicRequest request) {

        Music music = musicRepository.findById(new Integer(request.getId()).longValue()).get();

        List<io.spring.guides.gs_producing_web_service.Music> musicas = new ArrayList<>();
        List<io.spring.guides.gs_producing_web_service.Playlist> playlistSoap = new ArrayList<>();

        for (PlayList play : music.getPlaylists()) {
            io.spring.guides.gs_producing_web_service.Playlist playlist = new io.spring.guides.gs_producing_web_service.Playlist();

            playlist.setIdPlaylist(play.getPlayListId().intValue());
            playlist.setName(play.getName());

            playlistSoap.add(playlist);
        }

        return playlistSoap;
    }

    public List<io.spring.guides.gs_producing_web_service.Music> getAllMusics() {
        List<Music> musics = musicRepository.findAll();

        List<io.spring.guides.gs_producing_web_service.Music> musicas = new ArrayList<>();

        for (Music music : musics) {
            io.spring.guides.gs_producing_web_service.Music musica = new io.spring.guides.gs_producing_web_service.Music();

            musica.setIdMusic(music.getMusicId().intValue());
            musica.setName(music.getName());

            musicas.add(musica);
        }

        return musicas;
    }

    public List<io.spring.guides.gs_producing_web_service.Playlist> getAllPlaylists() {
        List<PlayList> plays = playlistRepository.findAll();

        List<io.spring.guides.gs_producing_web_service.Playlist> playlistSoap = new ArrayList<>();
        List<io.spring.guides.gs_producing_web_service.Music> musicas = new ArrayList<>();

        for (PlayList play : plays) {

            for (Music music : play.getMusics()) {
                io.spring.guides.gs_producing_web_service.Music musica = new io.spring.guides.gs_producing_web_service.Music();

                musica.setIdMusic(music.getMusicId().intValue());
                musica.setName(music.getName());

                musicas.add(musica);
            }

            io.spring.guides.gs_producing_web_service.Playlist playlist = new io.spring.guides.gs_producing_web_service.Playlist();

            playlist.setIdPlaylist(play.getPlayListId().intValue());
            playlist.setName(play.getName());

            musicas.forEach(musicSoap -> playlist.getMusics().add(musicSoap));


            playlistSoap.add(playlist);

        }

        return playlistSoap;
    }

    public List<io.spring.guides.gs_producing_web_service.Playlist> getPlaylistsByUser(GetPlaylistRequest request) {
        User user = userRepository.findById(new Integer(request.getIdUser()).longValue()).get();

        List<PlayList> plays = user.getPlaylists();

        List<io.spring.guides.gs_producing_web_service.Playlist> playlistSoap = new ArrayList<>();
        List<io.spring.guides.gs_producing_web_service.Music> musicas = new ArrayList<>();

        for (PlayList play : plays) {
            GetPlaylistRequest filter = new GetPlaylistRequest();

            filter.setId(play.getPlayListId().intValue());

            musicas = getMusicsByPlaylist(filter);

            io.spring.guides.gs_producing_web_service.Playlist playlist = new io.spring.guides.gs_producing_web_service.Playlist();

            playlist.setIdPlaylist(play.getPlayListId().intValue());
            playlist.setName(play.getName());

            musicas.forEach(musicSoap -> playlist.getMusics().add(musicSoap));


            playlistSoap.add(playlist);

        }

        return playlistSoap;
    }

    public List<io.spring.guides.gs_producing_web_service.User> getAllUsers() {
        List<User> users = userRepository.findAll();

        List<io.spring.guides.gs_producing_web_service.User> usersSoap = new ArrayList<>();
        List<io.spring.guides.gs_producing_web_service.Playlist> playlistSoap = new ArrayList<>();
        List<io.spring.guides.gs_producing_web_service.Music> musicas = new ArrayList<>();

        for (User user : users) {

            for (PlayList play : user.getPlaylists()) {
                GetPlaylistRequest filter = new GetPlaylistRequest();

                filter.setId(play.getPlayListId().intValue());

                musicas = getMusicsByPlaylist(filter);

                io.spring.guides.gs_producing_web_service.Playlist playlist = new io.spring.guides.gs_producing_web_service.Playlist();

                playlist.setIdPlaylist(play.getPlayListId().intValue());
                playlist.setName(play.getName());

                musicas.forEach(musicSoap -> playlist.getMusics().add(musicSoap));


                playlistSoap.add(playlist);

            }

            io.spring.guides.gs_producing_web_service.User userSoap = new io.spring.guides.gs_producing_web_service.User();

            userSoap.setIdUser(user.getUserId().intValue());
            userSoap.setName(user.getName());

            playlistSoap.forEach(psoap -> userSoap.getPlaylists().add(psoap));

            usersSoap.add(userSoap);
        }

        return usersSoap;
    }

    @PostConstruct
    public void initData() {

        List<User> musics = new ArrayList<>();

        int musicas = 50;
        int plays = 2;

        PlayList playlist = null;
        for (int y = 1; y <= plays; y++) {

            playlist = new PlayList("playlist " + new Random().nextInt(1000));

            for (int x = 1; x <= musicas; x++) {
                playlist.addMusic(new Music("musica " + new Random().nextInt(1000)));
            }

            playlistRepository.save(playlist);
        }

        List<PlayList> playlists = new ArrayList<>();
        for (int u = 1; u <= 5; u++) {
            User usuario = new User("usuario " + new Random().nextInt(1000));
            Long a = (long) new Random().nextInt(2);

            PlayList p = playlistRepository.findById(a == 0 ? a + 1 : a).get();

            playlists.add(p);

            usuario.setPlaylists(playlists);

            userRepository.save(usuario);

            playlists.clear();


        }
    }

}

