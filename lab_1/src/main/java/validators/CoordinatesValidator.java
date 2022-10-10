package validators;

import model.Error;
import model.dataForXml.JaxbCoordinates;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

public class CoordinatesValidator {
    public List<Error> validate(JaxbCoordinates coordinates) throws IllegalAccessException {
        List<Error> errorList = new ArrayList<>();
        if (coordinates == null) {
            return errorList;
        }
        for (Field f : JaxbCoordinates.class.getDeclaredFields()) {
            f.setAccessible(true);
            if (f.get(coordinates) == null) {
                errorList.add(new Error(700, f.getName(), String.format("Coordinates %s is not specified", f.getName())));
            }
        }
        if (coordinates.getY() <= -854) {
            errorList.add(new Error(701, "y", "Coordinates y should be more than -854"));
        }

        return errorList;
    }
}
