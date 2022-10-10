package ru.itmo.soalab2.validators;

import ru.itmo.soalab2.model.Coordinates;
import ru.itmo.soalab2.model.CoordinatesFromClient;
import ru.itmo.soalab2.model.Error;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

public class CoordinatesValidator {
    public Coordinates validate(CoordinatesFromClient coordinates) throws IllegalAccessException, ValidateFieldsException {
        List<Error> errorList = new ArrayList<>();
        Coordinates validatedCoordinates = new Coordinates();
        if (coordinates == null) {
            throw new ValidateFieldsException(errorList);
        }

        for (Field f : CoordinatesFromClient.class.getDeclaredFields()) {
            f.setAccessible(true);
            if (f.get(coordinates) == null) {
                errorList.add(new Error(700, f.getName(), String.format("Coordinates %s is not specified", f.getName())));
            }
        }

        try {
            Integer x = 0;
            if (coordinates.getX() != null) {
                x = Integer.parseInt(coordinates.getX());
            }
            if (x <= -60) {
                errorList.add(new Error(701, "x", "Coordinates y should be bigger than -60"));
            } else  {
                validatedCoordinates.setX(x);
            }
        } catch (NumberFormatException e) {
            errorList.add(new Error(702, "x", "The entered value is not an integer value"));
        }

        try {
            Long y = 0L;
            if (coordinates.getY() != null) {
                y = Long.parseLong(coordinates.getY());
            }
            if (y >= 498) {
                errorList.add(new Error(701, "y", "Coordinates y should be less than 498"));
            } else  {
                validatedCoordinates.setY(y);
            }
        } catch (NumberFormatException e) {
            errorList.add(new Error(702, "y", "The entered value is not an integer value"));
        }

        if (errorList.size() > 0) {
            throw new ValidateFieldsException(errorList);
        }
        if (coordinates.getId() != 0) validatedCoordinates.setId(coordinates.getId());
        return validatedCoordinates;
    }
}
