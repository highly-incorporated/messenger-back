package com.corporate.repository;

import com.corporate.model.Team;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;


public interface TeamRepository extends JpaRepository<Team, Integer> {
    Team findById(int team_id);

    @Transactional
    @Modifying
    @Query(value = "UPDATE team SET team_participants = :participants WHERE team_id = :team_id", nativeQuery = true)
    void updateParticipants(@Param("participants") String participants, @Param("team_id") int team_id);
}