package ru.itmo.soalab2.validators;

import ru.itmo.soalab2.model.*;
import ru.itmo.soalab2.model.Error;

import java.util.ArrayList;
import java.util.List;

public class TeamValidator {

    public Team validate(TeamFromClient team) throws IllegalAccessException, ValidateFieldsException {
        List<Error> errorList = new ArrayList<>();
        Team validatedTeam = new Team();

        if (team.getTeamName().isEmpty())
        {
            validatedTeam.setTeamName("-");
        }else {
            validatedTeam.setTeamName(team.getTeamName());
        }
        if (team.getId() != null) validatedTeam.setId(team.getId());
        return validatedTeam;
    }
}
