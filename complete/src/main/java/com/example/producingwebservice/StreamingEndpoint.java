package com.example.producingwebservice;

import io.spring.guides.gs_producing_web_service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;

@Endpoint
public class StreamingEndpoint {
	private static final String NAMESPACE_URI = "http://spring.io/guides/gs-producing-web-service";

	private GeneralStreamService generalStreamService;

	@Autowired
	public StreamingEndpoint(GeneralStreamService countryRepository) {
		this.generalStreamService = countryRepository;
	}

	@PayloadRoot(namespace = NAMESPACE_URI, localPart = "getUserRequest")
	@ResponsePayload
	public GetUserListResponse getUsers(@RequestPayload GetUserRequest request) {
		GetUserListResponse response = new GetUserListResponse();
		generalStreamService.getAllUsers().forEach(user -> response.getUsers().add(user));

		return response;
	}

	@PayloadRoot(namespace = NAMESPACE_URI, localPart = "getMusicRequest")
	@ResponsePayload
	public GetMusicListResponse getMusics(@RequestPayload GetMusicRequest request) {
		GetMusicListResponse response = new GetMusicListResponse();

		if (request.getIdPlaylist() != 0) {
			GetPlaylistRequest req = new GetPlaylistRequest();
			req.setId(request.getIdPlaylist());

			generalStreamService.getMusicsByPlaylist(req).forEach(user -> response.getMusics().add(user));
			return response;
		}

		generalStreamService.getAllMusics().forEach(user -> response.getMusics().add(user));

		return response;
	}

	@PayloadRoot(namespace = NAMESPACE_URI, localPart = "getPlaylistRequest")
	@ResponsePayload
	public GetPlaylistResponse getPlaylists(@RequestPayload GetPlaylistRequest request) {
		GetPlaylistResponse response = new GetPlaylistResponse();

		if (request.getIdMusica() != 0) {
			GetMusicRequest req = new GetMusicRequest();
			req.setId(request.getIdMusica());

			generalStreamService.getPlaylistByMusic(req).forEach(user -> response.getPlaylists().add(user));
			return response;
		}

		generalStreamService.getPlaylistsByUser(request).forEach(user -> response.getPlaylists().add(user));
		return response;
	}
}
