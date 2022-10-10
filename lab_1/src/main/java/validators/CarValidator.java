package validators;

import model.Error;
import model.dataForXml.JaxbCar;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

public class CarValidator {
    public List<Error> validate(JaxbCar car) throws IllegalAccessException {
        List<Error> errorList = new ArrayList<>();
        if (car == null) {
            return errorList;
        }
        for (Field f : JaxbCar.class.getDeclaredFields()) {
            f.setAccessible(true);
            if (f.get(car) == null) {
                errorList.add(new Error(700, f.getName(), String.format("Car %s is not specified", f.getName())));
            }
        }
        return errorList;
    }
}
