package io.github.karMiguel.library.services;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
@RequiredArgsConstructor
public class NewsAPIServices{


    @Value("${news.api.key}")
    private String apiKey;

    @Value("${news.api.url}")
    private String newsApiUrl;

    private final RestTemplate restTemplate;


    public String getTopHeadlines(String country, String category) {
        String url = newsApiUrl + "?country=" + country + "&category=" + category + "&apiKey=" + apiKey;
        return restTemplate.getForObject(url, String.class);
    }}

