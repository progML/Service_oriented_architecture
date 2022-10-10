package ru.itmo.soalab2.repo;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ru.itmo.soalab2.model.HumanBeing;

import javax.transaction.Transactional;

@Repository
public interface HumanBeingRepository extends CrudRepository<HumanBeing, Long> {
    @Modifying
    @Transactional
    @Query(nativeQuery = true, value = "INSERT INTO team (team_name) VALUES (?1)")
    void createNewTeam(String name);

    @Modifying
    @Transactional
    @Query(nativeQuery = true, value = "INSERT INTO team (team_name) VALUES (?1)")
    void addTeam(Long id);


    @Query(nativeQuery = true, value = "SELECT id from team WHERE team_name = (?1) LIMIT 1")
    Long findIdByName(String teamName);

    @Query(nativeQuery = true, value = "SELECT name from human_being WHERE id = :heroId")
    String findHumanBeingNameById(@Param("heroId") Long heroId);


    @Modifying
    @Transactional
    @Query(nativeQuery = true, value = "UPDATE human_being SET team_id = (?1) WHERE id = (?2)")
    void updateTeam(Long teamId, Long humanId);
}
