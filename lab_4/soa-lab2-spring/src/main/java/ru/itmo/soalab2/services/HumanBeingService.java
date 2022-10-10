package ru.itmo.soalab2.services;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import ru.itmo.soalab2.controller.HumanBeingRequestParams;
//import ru.itmo.soalab2.dto.HumanBeingDto;
import ru.itmo.soalab2.dto.HumanBeingDto;
import ru.itmo.soalab2.model.*;
import ru.itmo.soalab2.repo.HumanBeingFilterSpecification;
import ru.itmo.soalab2.repo.HumanBeingRepository;
import ru.itmo.soalab2.validators.HumanBeingValidator;
import ru.itmo.soalab2.validators.ValidateFieldsException;

import java.text.ParseException;
import java.time.LocalDateTime;
import java.util.List;

public class HumanBeingService {
    private final HumanBeingValidator humanBeingValidator;
    private final HumanBeingRepository humanBeingRepository;

    public HumanBeingService(HumanBeingRepository humanBeingRepository) {
        this.humanBeingValidator = new HumanBeingValidator();
        this.humanBeingRepository = humanBeingRepository;
    }

    public ResponseEntity<?> getAllHumanBeings(HumanBeingRequestParams filterParams) {
        HumanBeingFilterSpecification spec = new HumanBeingFilterSpecification(filterParams);
        try {
            Sort currentSorting = filterParams.parseSorting();
            Pageable sortedBy = PageRequest.of(filterParams.page, filterParams.size, currentSorting);
            Page<HumanBeing> res = humanBeingRepository.findAll(spec, sortedBy);
            long count = humanBeingRepository.count(spec);
            PaginationResult r = new PaginationResult(filterParams.size, filterParams.page, count, res.getContent());
            return ResponseEntity.status(200).body(r);
        } catch (ParseException e) {
            return ResponseEntity.status(404).body(e.getMessage());
        }
    }

    public ResponseEntity<?> createHumanBeing(HumanBeingFromClient newHumanBeing) throws Exception{
        try {
            HumanBeing validHumanBeing = humanBeingValidator.validate(newHumanBeing);
            validHumanBeing.setCreationDate(LocalDateTime.now());
            Long id = humanBeingRepository.save(validHumanBeing).getId();
            return ResponseEntity.status(201).body(new OperationResponse(id, "HumanBeing created successfully"));
        } catch (ValidateFieldsException ex) {
            return sendErrorList(ex);
        }
    }

    public ResponseEntity<?> updateHumanBeing(HumanBeingFromClient updatedHumanBeing) throws Exception {
        try {
            HumanBeing validHumanBeing = humanBeingValidator.validate(updatedHumanBeing);
            boolean isFound = humanBeingRepository.existsById(updatedHumanBeing.getId());
            if (isFound) {
                validHumanBeing.setCreationDate(humanBeingRepository.findCreationDateByHumanBeingId(updatedHumanBeing.getId()));
                humanBeingRepository.save(validHumanBeing);
                return ResponseEntity.status(200).body(new OperationResponse(updatedHumanBeing.getId(), "City updated successfully"));
            } else {
                return ResponseEntity.status(404).body(new OperationResponse(updatedHumanBeing.getId(), "Cannot find city with id " + updatedHumanBeing.getId()));
            }
        } catch (ValidateFieldsException ex) {
            return sendErrorList(ex);
        }
    }

    public ResponseEntity<?> deleteHumanBeing(Long id) {
        boolean isFound = humanBeingRepository.existsById(id);
        humanBeingRepository.deleteHumanBeingById(id);
        if (isFound) {
            return ResponseEntity.status(200).body(new OperationResponse(id, "City deleted successfully"));
        } else {
            return ResponseEntity.status(404).body(new OperationResponse(id, "Cannot find city with id " + id));
        }
    }

    public ResponseEntity<?> getHumanBeings(Long id) {
        return ResponseEntity.status(200).body(humanBeingRepository.findById(id));
    }

    public boolean deleteMood(String mood) {
        if (humanBeingRepository.findIdByMood(mood).isEmpty()) {
            return false;
        } else {
            humanBeingRepository.deleteById(humanBeingRepository.findIdByMood(mood).get(0));
            return true;
        }
    }

    private ResponseEntity<?> sendErrorList(ValidateFieldsException ex) {
        return ResponseEntity.status(400).body(ex.getErrorMsg());
    }

    public List<HumanBeing> findAllName(String name){
        return humanBeingRepository.findAllByNameStartingWith(name);
    }

    public void createTeam(String name){
        humanBeingRepository.createNewTeam(name);
    }

    public boolean addTeam(Long heroId, HumanBeingDto humanBeingDto){
        boolean res;
        String teamName = humanBeingDto.getTeamName();
        String heroName = humanBeingRepository.findHumanBeingNameById(heroId);
        Long teamId = humanBeingRepository.findIdByName(teamName);
        if (heroName != null && teamId != null) {
            humanBeingRepository.updateTeam(teamId, heroId);
            res = true;
        }else {
            res = false;
        }
        return res;
    }


}
