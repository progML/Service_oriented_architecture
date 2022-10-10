package ru.itmo.soalab2.services;

import ru.itmo.soalab2.dto.HumanBeingDto;
import ru.itmo.soalab2.repo.HumanBeingRepository;
import ru.itmo.soalab2.rest.RestClient;

public class HumanBeingService {

    private final HumanBeingRepository humanBeingRepository;
    private final RestClient restClient;

    public HumanBeingService(HumanBeingRepository humanBeingRepository, RestClient restClient) {
        this.humanBeingRepository = humanBeingRepository;
        this.restClient = restClient;
    }

    public void createTeam(String name) {
        restClient.createNewTeam(name);
    }

    public void addTeam(Long heroId, HumanBeingDto humanBeingDto) {
        restClient.addHumanToTeamById(heroId, humanBeingDto);
    }
}
