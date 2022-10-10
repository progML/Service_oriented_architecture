package ru.itmo.soalab2.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
//import ru.itmo.soalab2.dto.HumanBeingDto;
import ru.itmo.soalab2.dto.HumanBeingDto;
import ru.itmo.soalab2.model.HumanBeing;
import ru.itmo.soalab2.model.HumanBeingFromClient;
import ru.itmo.soalab2.repo.HumanBeingRepository;
import ru.itmo.soalab2.services.HumanBeingService;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/humanBeing")
public class HumanBeingController {

    private HumanBeingService humanBeingService;

    public HumanBeingController(HumanBeingRepository humanBeingRepository) {
        this.humanBeingService = new HumanBeingService(humanBeingRepository);
    }

    private HumanBeingRequestParams getFilterParams(Map<String, String[]> map) {
        return new HumanBeingRequestParams(map);
    }

    @GetMapping
    ResponseEntity<?> getAllHumanBeing(HttpServletRequest httpServletRequest) {
        Map<String, String[]> requestParameterMap = httpServletRequest.getParameterMap();
        HumanBeingRequestParams filterParams = this.getFilterParams(requestParameterMap);
        return humanBeingService.getAllHumanBeings(filterParams);
    }

    @PostMapping
    ResponseEntity<?> addHumanBeing(@RequestBody HumanBeingFromClient newHumanBeing) throws Exception {
        return humanBeingService.createHumanBeing(newHumanBeing);
    }

    @PutMapping(value = "/{id}")
    ResponseEntity<?> updateHumanBeing(@RequestBody HumanBeingFromClient updatedHumanBeing) throws Exception {
        return humanBeingService.updateHumanBeing(updatedHumanBeing);
    }

    @DeleteMapping(value = "/{id}")
    ResponseEntity<?> deleteHumanBeing(@PathVariable Long id) {
        return humanBeingService.deleteHumanBeing(id);
    }

    @GetMapping("/{id}")
    ResponseEntity<?> findById(@PathVariable Long id) {
        return humanBeingService.getHumanBeings(id);
    }


    @DeleteMapping("/deleteMood/{mood}")
    public ResponseEntity<String> deleteMood(@PathVariable String mood) {
        if (humanBeingService.deleteMood(mood)) {
            return new ResponseEntity<>("Mood was delete", HttpStatus.OK);
        }else {
            return new ResponseEntity<>("No mood", HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/findName/{name}")
    @ResponseStatus(HttpStatus.OK)
    public List<HumanBeing> getAllName(@PathVariable String name) {
        return humanBeingService.findAllName(name);
    }

    @GetMapping(value = "/create/{name}")
    @ResponseStatus(HttpStatus.OK)
    void createTeam(@PathVariable String name) {
        humanBeingService.createTeam(name);
    }

    @PostMapping(value = "/add/{heroId}")
//    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<String> addTeam(@PathVariable Long heroId, @RequestBody HumanBeingDto humanBeingDto) {
        if (humanBeingService.addTeam(heroId, humanBeingDto)){
            return new ResponseEntity<>("Added", HttpStatus.OK);
        } else {
            return new ResponseEntity<>("No added", HttpStatus.NOT_FOUND);
        }
    }
}
