package ru.itmo.soalab2.Soap;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class TeamRepository {

    private final RestClient restClient;

    public TeamRepository(RestClient restClient) {
        this.restClient = restClient;
    }


    public void creatTeam(String name){
        restClient.createNewTeam(name);
    }

    public void addTeam(Long heroId, String teamName) {
        restClient.addHumanToTeamById(heroId, teamName);
    }

}
