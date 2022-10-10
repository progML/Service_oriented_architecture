package ru.itmo.soalab2.controller;

import org.springframework.data.domain.Sort;
import ru.itmo.soalab2.model.*;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.Root;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class HumanBeingRequestParams {
    public String name;
    public String[] x;
    public String[] y;
    public String[] creationDate;
    public String realHero;
    public String hasToothpick;
    public String[] impactSpeed;
    public String[] minutesOfWaiting;
    public String[] weaponType;
    public String[] mood;
    public String cool;
    public String teamName;
    public String[] sort;
    public int page;
    public int size;

    private static final String NAME_PARAM = "name";
    private static final String X_PARAM = "x";
    private static final String Y_PARAM = "y";
    private static final String CREATION_DATE_PARAM = "creationDate";
    private static final String REAL_HERO_PARAM = "realHero";
    private static final String HAS_TOOTHPICK_PARAM = "hasToothpick";
    private static final String IMPACT_SPEED_PARAM = "impactSpeed";
    private static final String MINUTES_OF_WAITING_PARAM = "minutesOfWaiting";
    private static final String WEAPON_TYPE_PARAM = "weaponType";
    private static final String MOOD_PARAM = "mood";
    private static final String COOL = "cool";
    private static final String TEAM_NAME = "teamName";
    private static final String SORTING_PARAM = "sort";
    private static final String PAGE_INDEX = "page";
    private static final String PAGE_SIZE_PARAM = "size";


    HumanBeingRequestParams(Map<String, String[]> info) {
        setCityRequestParams(info.get(NAME_PARAM),
                info.get(X_PARAM),
                info.get(Y_PARAM),
                info.get(CREATION_DATE_PARAM),
                info.get(REAL_HERO_PARAM),
                info.get(HAS_TOOTHPICK_PARAM),
                info.get(IMPACT_SPEED_PARAM),
                info.get(MINUTES_OF_WAITING_PARAM),
                info.get(WEAPON_TYPE_PARAM),
                info.get(MOOD_PARAM),
                info.get(COOL),
                info.get(TEAM_NAME),
                info.get(SORTING_PARAM),
                info.get(PAGE_INDEX),
                info.get(PAGE_SIZE_PARAM)
        );
    }

    private void setCityRequestParams(String[] name,
                                      String[] x,
                                      String[] y,
                                      String[] creationDate,
                                      String[] realHero,
                                      String[] hasToothpick,
                                      String[] impactSpeed,
                                      String[] minutesOfWaiting,
                                      String[] weaponType,
                                      String[] mood,
                                      String[] cool,
                                      String[] teamName,
                                      String[] sort,
                                      String[] page,
                                      String[] size) {
        this.name = name == null ? null : name[0];
        this.x = x;
        this.y = y;
        this.creationDate = creationDate;
        this.realHero = realHero == null ? null : realHero[0];
        this.hasToothpick = hasToothpick == null ? null : hasToothpick[0];
        this.impactSpeed = impactSpeed;
        this.minutesOfWaiting = minutesOfWaiting;
        this.weaponType = weaponType;
        this.mood = mood;
        this.cool = cool == null ? null : cool[0];
        this.teamName = teamName == null ? null : teamName[0];
        this.sort = sort;
        this.page = page == null ? 0 : Integer.parseInt(page[0]);
        this.size = size == null ? 5 : Integer.parseInt(size[0]);
    }

    private String like(String val) {
        return "%" + val + "%";
    }

    public List<javax.persistence.criteria.Predicate> getPredicates(
            CriteriaBuilder cb,
            Root<HumanBeing> root,
            Join<HumanBeing, Coordinates> joinCoordinates,
            Join<HumanBeing, Car> joinCar,
            Join<HumanBeing, Team> joinTeam
    ) throws ParseException {
        List<javax.persistence.criteria.Predicate> predicates = new ArrayList<>();
        if (name != null)
            predicates.add(cb.like(root.get("name"), like(name)));

        if (x != null)
            if (x.length > 1) {
                if (x[0] != null && !x[0].isEmpty())
                    // больше или равен
                    predicates.add(cb.ge(joinCoordinates.get("x"), Integer.parseInt(x[0])));
                if (x[1] != null && !x[1].isEmpty())
                    // меньше или равен
                    predicates.add(cb.le(joinCoordinates.get("x"), Integer.parseInt(x[1])));
            } else if (x[0] != null && !x[0].isEmpty())
                predicates.add(cb.equal(joinCoordinates.get("x"), Integer.parseInt(x[0])));

        if (y != null)
            if (y.length > 1) {
                if (y[0] != null && !y[0].isEmpty())
                    predicates.add(cb.ge(joinCoordinates.get("y"), Long.parseLong(y[0])));
                if (y[1] != null && !y[1].isEmpty())
                    predicates.add(cb.le(joinCoordinates.get("y"), Long.parseLong(y[1])));
            } else if (y[0] != null && !y[0].isEmpty())
                predicates.add(cb.equal(joinCoordinates.get("y"), Long.parseLong(y[0])));

        if (creationDate != null)
            if (creationDate.length > 1) {
                System.out.println(creationDate);
                if (creationDate[0] != null && !creationDate[0].isEmpty())
                    predicates.add(cb.greaterThanOrEqualTo(root.get("creationDate"), new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'").parse(creationDate[0])));
                if (creationDate[1] != null && !creationDate[1].isEmpty())
                    predicates.add(cb.lessThanOrEqualTo(root.get("creationDate"), new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'").parse(creationDate[1])));
            } else if (creationDate[0] != null && !creationDate[0].isEmpty())
                predicates.add(cb.equal(root.get("creationDate"), new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'").parse(creationDate[0])));


        if (realHero != null) {
            if (realHero.equals("true")) {
                predicates.add(cb.isTrue(root.get("realHero").as(Boolean.class)));
            } else {
                predicates.add(cb.isFalse(root.get("realHero").as(Boolean.class)));
            }
        }
        if (hasToothpick != null) {
            if (hasToothpick.equals("true")) {
                predicates.add(cb.isTrue(root.get("hasToothpick").as(Boolean.class)));
            } else {
                predicates.add(cb.isFalse(root.get("hasToothpick").as(Boolean.class)));
            }
        }

        if (impactSpeed != null) {
            predicates.add(cb.equal(root.get("impactSpeed"), Integer.parseInt(impactSpeed[0])));
        }

        if (minutesOfWaiting != null)
            if (minutesOfWaiting.length > 1) {
                if (minutesOfWaiting[0] != null && !minutesOfWaiting[0].isEmpty())
                    predicates.add(cb.ge(root.get("minutesOfWaiting"), Integer.parseInt(minutesOfWaiting[0])));
                if (minutesOfWaiting[1] != null && !minutesOfWaiting[1].isEmpty())
                    predicates.add(cb.le(root.get("minutesOfWaiting"), Integer.parseInt(minutesOfWaiting[1])));
            } else if (minutesOfWaiting[0] != null && !minutesOfWaiting[0].isEmpty())
                predicates.add(cb.equal(root.get("minutesOfWaiting"), Integer.parseInt(minutesOfWaiting[0])));

        if (weaponType != null)
            predicates.add(root.get("weaponType").as(String.class).in(weaponType));

        if (mood != null)
            predicates.add(root.get("mood").as(String.class).in(mood));

        if (cool != null) {
            if (cool.equals("true")) {
                predicates.add(cb.isTrue(joinCar.get("cool").as(Boolean.class)));
            } else {
                predicates.add(cb.isFalse(joinCar.get("cool").as(Boolean.class)));
            }
        }

        if (teamName != null)
            predicates.add(cb.like(joinTeam.get("teamName"), like(teamName)));

        return predicates;
    }

    public Sort parseSorting() throws ParseException {
        String[] args = sort[0].split("_", 2);
        if (args.length != 2)
            throw new ParseException("incorrect sort parameter " + sort[0], 0);
        if (args[0].equals(X_PARAM) || args[0].equals(Y_PARAM)) {
            args[0] = "coordinates." + args[0];
        }
        if (args[0].equals(TEAM_NAME)) {
            args[0] = "team." + args[0];
        }
        if (args[0].equals(COOL)) {
            args[0] = "car." + args[0];
        }
        String field = args[0];
        Sort currentSorting = Sort.by(field);
        if (args[1].equals("asc"))
            currentSorting = currentSorting.ascending();
        else if (args[1].equals("desc"))
            currentSorting = currentSorting.descending();
        else
            throw new ParseException("incorrect sort parameter " + sort[0], 0);
        return currentSorting;
    }
}
