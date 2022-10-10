package ru.itmo.soalab2.services;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
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

    public void createTeam(String name){
        restClient.createNewTeam(name);
//        humanBeingRepository.createNewTeam(name);
    }

//    public void addTeam(Long heroId, HumanBeingDto humanBeingDto) {
//        restClient.addHumanToTeamById(heroId, humanBeingDto);
////       String teamName = humanBeingDto.getTeamName();
////       String heroName = humanBeingRepository.findHumanBeingNameById(heroId);
////       Long teamId = humanBeingRepository.findIdByName(teamName);
////       if (heroName != null && teamId != null) {
////           humanBeingRepository.updateTeam(teamId, heroId);
////       }else
////           System.out.println("не гуд");
////    }
//    }
}
