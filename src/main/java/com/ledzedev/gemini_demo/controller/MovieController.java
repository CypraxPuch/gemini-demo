package com.ledzedev.gemini_demo.controller;

import com.ledzedev.gemini_demo.model.MovieRecommendation;
import com.ledzedev.gemini_demo.service.MovieService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
public class MovieController {

    private final MovieService movieService;

    public MovieController(MovieService movieService) {
        this.movieService = movieService;
    }

    @GetMapping("/recommend")
    public MovieRecommendation recommend(@RequestParam(defaultValue = "mind-bending sci-fi with a plot twist") String prompt){
        log.debug("Recommendation controller.");
        return movieService.getRecommendation(prompt);
    }
}
