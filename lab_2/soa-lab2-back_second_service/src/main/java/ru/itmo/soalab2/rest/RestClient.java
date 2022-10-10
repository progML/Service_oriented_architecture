package ru.itmo.soalab2.rest;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import ru.itmo.soalab2.dto.HumanBeingDto;
import ru.itmo.soalab2.exceptions.RestTemplateResponseErrorHandler;
import ru.itmo.soalab2.model.Team;

import java.util.*;

@Service
public class RestClient {
    HttpHeaders headers = new HttpHeaders();
    private static String URI = "http://localhost:8080/api/humanBeing";
    private final RestTemplateBuilder builder = new RestTemplateBuilder();
    private final RestTemplate restTemplate = builder.errorHandler(new RestTemplateResponseErrorHandler()).build();

    public ResponseEntity createNewTeam(String name){
        final ResponseEntity<String> strEntity =
                restTemplate.getForEntity(URI + "/create/" + name, String.class);
        return strEntity;
    }

    public void addHumanToTeamById(Long id, HumanBeingDto humanBeingDto){
        headers.setContentType(MediaType.APPLICATION_JSON);
        JSONObject personJsonObject = new JSONObject();
        try {
            personJsonObject.put("teamName", humanBeingDto.getTeamName());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        HttpEntity<String> entity = new HttpEntity<String>(personJsonObject.toString(), headers);
        restTemplate.postForObject(URI + "/add/" + id, entity, String.class);
    }
}
