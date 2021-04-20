package com.tt.league.champion.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.tt.league.champion.model.League;

public interface ILeagueRepository extends JpaRepository<League, Long>{

}
