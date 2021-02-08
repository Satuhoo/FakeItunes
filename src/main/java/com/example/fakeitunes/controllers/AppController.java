package com.example.fakeitunes.controllers;

import com.example.fakeitunes.data_access.MusicRepository;
import com.example.fakeitunes.models.SearchResult;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;

@Controller
public class AppController {
    MusicRepository musicRepository = new MusicRepository();

    @GetMapping("/")
    public String getRandomMusic(Model model){
        model.addAttribute("artists", musicRepository.getFiveRandomArtists());
        model.addAttribute("songs", musicRepository.getFiveRandomSongs());
        model.addAttribute("genres", musicRepository.getFiveRandomGenres());
        return "home";
    }

    @RequestMapping("/search")
    public String search(Model model, @RequestParam("term") String term) {
        ArrayList<SearchResult> results = musicRepository.search(term);
        model.addAttribute("results", results);
        model.addAttribute("term", term);
        return "search";
    }
}
