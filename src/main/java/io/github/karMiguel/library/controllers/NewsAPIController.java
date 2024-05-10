package io.github.karMiguel.library.controllers;

import io.github.karMiguel.library.services.NewsAPIServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/news/top-headlines")
public class NewsAPIController {

    @Autowired
    private  NewsAPIServices newsAPIService;

    @GetMapping
    public ResponseEntity<String> getTopHeadlines(
            @RequestParam(name = "country", defaultValue = "br") String country,
            @RequestParam(name = "category", defaultValue = "technology") String category) {
        return ResponseEntity.ok(newsAPIService.getTopHeadlines(country, category));
    }
}
