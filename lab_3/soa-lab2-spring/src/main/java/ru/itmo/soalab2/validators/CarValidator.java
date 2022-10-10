package ru.itmo.soalab2.validators;

import ru.itmo.soalab2.model.Error;
import ru.itmo.soalab2.model.Car;
import ru.itmo.soalab2.model.CarFromClient;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

public class CarValidator {
    public Car validate(CarFromClient car) throws IllegalAccessException, ValidateFieldsException {
        List<Error> errorList = new ArrayList<>();
        Car validatedCar = new Car();
        validatedCar.setCool(car.isCool());

        if (car == null) {
            throw new ValidateFieldsException(errorList);
        }

        for (Field f : CarFromClient.class.getDeclaredFields()) {
            f.setAccessible(true);
            if (f.get(car) == null) {
                errorList.add(new Error(700, f.getName(), String.format("Human %s is not specified", f.getName())));
            }
        }
        //todo: валидатор cool
        validatedCar.setCool(car.isCool());

        if (errorList.size() > 0) {
            throw new ValidateFieldsException(errorList);
        }
        if (car.getId() != 0) validatedCar.setId(car.getId());
        return validatedCar;
    }
}
