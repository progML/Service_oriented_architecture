package ru.itmo.soalab2.repo;

import org.springframework.data.jpa.domain.Specification;
import ru.itmo.soalab2.controller.HumanBeingRequestParams;
import ru.itmo.soalab2.model.HumanBeing;
import ru.itmo.soalab2.model.Coordinates;
import ru.itmo.soalab2.model.Car;
import ru.itmo.soalab2.model.Team;

import javax.persistence.criteria.*;
import java.text.ParseException;

public class HumanBeingFilterSpecification implements Specification<HumanBeing> {

    private HumanBeingRequestParams filterParams;

    public HumanBeingFilterSpecification(HumanBeingRequestParams filterParams) {
        this.filterParams = filterParams;
    }

    @Override
    public Predicate toPredicate(Root<HumanBeing> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
        Join<HumanBeing, Coordinates> coordinatesJoin =  root.join("coordinates");
        Join<HumanBeing, Car> carJoin =  root.join("car");
        Join<HumanBeing, Team> teamJoin =  root.join("team");
        try {
            return criteriaBuilder.and(filterParams.getPredicates(criteriaBuilder,root, coordinatesJoin, carJoin, teamJoin).toArray(new Predicate[0]));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }
}
