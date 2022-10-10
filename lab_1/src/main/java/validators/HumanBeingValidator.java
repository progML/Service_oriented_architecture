package validators;

import model.Car;
import model.dataForXml.JaxbHumanBeing;

import model.Error;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

public class HumanBeingValidator {

    private final CarValidator carValidator;
    private final CoordinatesValidator coordinatesValidator;

    public HumanBeingValidator() {
        carValidator = new CarValidator();
        coordinatesValidator = new CoordinatesValidator();
    }

    public List<Error> validate(JaxbHumanBeing humanBeing) throws IllegalAccessException, ValidateFieldsException {
        List<Error> errorList = new ArrayList<>();
        for (Field f : JaxbHumanBeing.class.getDeclaredFields()) {
            f.setAccessible(true);
            if (f.get(humanBeing) == null) {
                errorList.add(new Error(700, f.getName(), (String.format("HumanBeing %s is not specified", f.getName()))));
            }
        }
        if (humanBeing.getName() != null && humanBeing.getName().isEmpty()) {
            errorList.add(new Error(701, "name", "HumanBeing name should not be empty"));
        }
        if (humanBeing.getCoordinates().getX() == 0 || humanBeing.getCoordinates().getY() == 0) {
            errorList.add(new Error(701, "coordinates", "HumanBeing coordinates should not be empty"));
        }
        errorList.addAll(carValidator.validate(humanBeing.getCar()));
        errorList.addAll(coordinatesValidator.validate(humanBeing.getCoordinates()));

        if (errorList.size() > 0) {
            throw new ValidateFieldsException(errorList);
        }

//
        return errorList;
    }
}
