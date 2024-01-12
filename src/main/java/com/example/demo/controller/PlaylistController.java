package com.example.demo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.example.demo.entity.Playlist;
import com.example.demo.entity.Song;
import com.example.demo.services.PlaylistService;
import com.example.demo.services.SongService;
@Controller
public class PlaylistController {
	@Autowired
	SongService ss;
	@Autowired
	PlaylistService ps;
	@GetMapping("/createPlaylist")
	public String addPlaylist(Model model)
	{
		List<Song> songList=ss.fetchAllSongs();
		model.addAttribute("songs", songList);
		return "createPlaylist";
	}
	@PostMapping("/addPlaylist")
	public String addPlaylist(@ModelAttribute Playlist playlist)
	{
		ps.addPlaylist(playlist);
		
		System.out.println(playlist);
		List<Song> songList=playlist.getSongs();
		for(Song s:songList)
		{
			s.getPlaylists().add(playlist);
			ss.updateSong(s);
		}
		
		return "adminHome";
	}
	@GetMapping("/viewPlaylists")
	public String viewPlaylist(Model model)
	{
		List<Playlist> allplaylists=ps.fetchAllPlaylists();
		model.addAttribute("allplaylists",allplaylists);
		return"displayPlaylists";
	}

}
