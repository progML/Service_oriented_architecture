package ru.itmo.soalab2.Soap;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;
import progml.team.GetAddTeamRequest;
import progml.team.GetAddTeamResponse;
import progml.team.GetCreateTeamRequest;
import progml.team.GetCreateTeamResponse;

@Endpoint
public class TeamEndpoint {

    private static final String NAMESPACE_URI = "http://progML/team";

    private final TeamRepository teamRepository;

    @Autowired
    public TeamEndpoint(TeamRepository teamRepository){
        this.teamRepository = teamRepository;
    }

    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "getCreateTeamRequest")
    @ResponsePayload
    public GetCreateTeamResponse creatTeam(@RequestPayload GetCreateTeamRequest getCreateTeamRequest){
        GetCreateTeamResponse response = new GetCreateTeamResponse();
        teamRepository.creatTeam(getCreateTeamRequest.getName());
        response.setState("Ok");
        return response;
    }

    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "getAddTeamRequest")
    @ResponsePayload
    public GetAddTeamResponse addTeam(@RequestPayload GetAddTeamRequest getAddTeamRequest){
        GetAddTeamResponse response = new GetAddTeamResponse();
        teamRepository.addTeam(getAddTeamRequest.getHeroId(), getAddTeamRequest.getHumanBeing().getTeamName());
        response.setState("Ok");
        return response;
    }

}
