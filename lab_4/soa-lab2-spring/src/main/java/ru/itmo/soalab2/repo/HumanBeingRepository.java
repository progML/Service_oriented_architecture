package ru.itmo.soalab2.repo;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ru.itmo.soalab2.model.HumanBeing;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.util.List;

@Repository
public interface HumanBeingRepository extends PagingAndSortingRepository<HumanBeing, Long>, JpaSpecificationExecutor<HumanBeing> {

    @Modifying
    @Transactional
    @Query(nativeQuery = true, value = "INSERT INTO team (team_name) VALUES (?1)")
    void createNewTeam(String name);

    @Query("SELECT creationDate FROM HumanBeing where id = ?1 ")
    LocalDateTime findCreationDateByHumanBeingId(long id);

    @Modifying
    @Transactional
    @Query(nativeQuery = true, value = "DELETE FROM human_being where id = ?1")
    void deleteHumanBeingById(Long id);

    @Transactional
    @Query(nativeQuery = true, value = "SELECT id FROM human_being WHERE mood =:mood")
    List<Long> findIdByMood(String mood);

    @Query(nativeQuery = true, value = "SELECT id from team WHERE team_name = (?1) LIMIT 1")
    Long findIdByName(String teamName);

    @Query(nativeQuery = true, value = "SELECT name from human_being WHERE id = :heroId")
    String findHumanBeingNameById(@Param("heroId") Long heroId);


    @Modifying
    @Transactional
    @Query(nativeQuery = true, value = "UPDATE human_being SET team_id = (?1) WHERE id = (?2)")
    void updateTeam(Long teamId, Long humanId);

    List<HumanBeing> findAllByNameStartingWith(String name);
}
