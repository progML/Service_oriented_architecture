package ru.itmo.soalab2.validators;

import ru.itmo.soalab2.model.*;
import ru.itmo.soalab2.model.Error;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

public class HumanBeingValidator {
    private final CarValidator carValidator;
    private final CoordinatesValidator coordinatesValidator;
    private final TeamValidator teamValidator;

    public HumanBeingValidator() {
        carValidator = new CarValidator();
        coordinatesValidator = new CoordinatesValidator();
        teamValidator = new TeamValidator();
    }

    public HumanBeing validate(HumanBeingFromClient humanBeing) throws IllegalAccessException, ValidateFieldsException {

        List<Error> errorList = new ArrayList<>();
        HumanBeing validatedHumanBeing = new HumanBeing();



        try {
            if (humanBeing.getCoordinates() != null) validatedHumanBeing.setCoordinates(coordinatesValidator.validate(humanBeing.getCoordinates()));
        } catch (ValidateFieldsException ex) {
            errorList.addAll(ex.getErrorMsg());
        }

        try {
            if (humanBeing.getCar() != null) validatedHumanBeing.setCar(carValidator.validate(humanBeing.getCar()));
        } catch (ValidateFieldsException ex) {
            errorList.addAll(ex.getErrorMsg());
        }

        try {
            if (humanBeing.getTeam() != null) validatedHumanBeing.setTeam(teamValidator.validate(humanBeing.getTeam()));
        } catch (ValidateFieldsException ex) {
            errorList.addAll(ex.getErrorMsg());
        }



        if (humanBeing.getName() != null &&humanBeing.getName().isEmpty()) {
            errorList.add(new Error(701,"name", "HumanBeing name should not be empty"));
        }  else {
            validatedHumanBeing.setName(humanBeing.getName());
        }
        // проверка на соответствие типу
        validatedHumanBeing.setRealHero(humanBeing.getRealHero());
        validatedHumanBeing.setHasToothpick(humanBeing.isHasToothpick());

        if (humanBeing.getImpactSpeed() != null) {
            validatedHumanBeing.setImpactSpeed(humanBeing.getImpactSpeed());
        } else {
            errorList.add(new Error(701,"name", "ImpactSpeed name should not be empty"));
        }

        validatedHumanBeing.setMinutesOfWaiting(humanBeing.getMinutesOfWaiting());

        validatedHumanBeing.setMood(humanBeing.getMood());
        validatedHumanBeing.setWeaponType(humanBeing.getWeaponType());

        if (errorList.size() > 0) {
            throw new ValidateFieldsException(errorList);
        }



        return validatedHumanBeing;
    }
}
