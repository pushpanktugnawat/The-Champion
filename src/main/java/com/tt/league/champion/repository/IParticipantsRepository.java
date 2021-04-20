package com.tt.league.champion.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.tt.league.champion.model.Participants;

@Repository
public interface IParticipantsRepository extends JpaRepository<Participants, Long>{

}
