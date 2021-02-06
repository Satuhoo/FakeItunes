package com.example.fakeitunes.controllers;

import com.example.fakeitunes.data_access.MusicRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class AppController {
    MusicRepository musicRepository = new MusicRepository();

    @GetMapping("/")
    public String getAllCustomers(Model model){
        model.addAttribute("artists", musicRepository.getFiveRandomArtists());
        model.addAttribute("songs", musicRepository.getFiveRandomSongs());
        model.addAttribute("genres", musicRepository.getFiveRandomGenres());
        return "home";
    }

    @GetMapping("/search")
    public String searchResult(@RequestParam String term) {
        return "Search with " + term;
    }
}
