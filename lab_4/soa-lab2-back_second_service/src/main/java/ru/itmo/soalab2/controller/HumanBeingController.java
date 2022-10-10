package ru.itmo.soalab2.controller;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.itmo.soalab2.dto.HumanBeingDto;
import ru.itmo.soalab2.repo.HumanBeingRepository;
import ru.itmo.soalab2.rest.RestClient;
import ru.itmo.soalab2.services.HumanBeingService;

@RestController
@RequestMapping("/api/team")
public class HumanBeingController {

    private HumanBeingService humanBeingService;

    public HumanBeingController(HumanBeingRepository humanBeingRepository, RestClient restClient) {
        this.humanBeingService = new HumanBeingService(humanBeingRepository, restClient);
    }

    @GetMapping(value = "create/{name}")
    @ResponseStatus(HttpStatus.OK)
    void createTeam(@PathVariable String name) {
        humanBeingService.createTeam(name);
    }

//    @PostMapping(value = "add/{heroId}")
//    @ResponseStatus(HttpStatus.OK)
//    void addTeam(@PathVariable Long heroId, @RequestBody HumanBeingDto humanBeingDto) {
//        humanBeingService.addTeam(heroId, humanBeingDto);
//    }

}
