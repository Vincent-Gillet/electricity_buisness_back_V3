package com.electricitybuisness.api.service;

import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

@Service
public class AdresseExternalService {
    private final RestTemplate restTemplate = new RestTemplate();

    public String searchAdresses(String query, int limit) {
        String url = UriComponentsBuilder.fromHttpUrl("https://api-adresse.data.gouv.fr/search/")
                .queryParam("q", query)
                .queryParam("limit", limit)
                .toUriString();
        String response = restTemplate.getForObject(url, String.class);
        System.out.println("External API response: " + response);
        return response;
/*
        return restTemplate.getForObject(url, String.class);
*/
    }
}
