package com.tt.league.champion.service;

import java.util.List;

import com.tt.league.champion.model.League;
import com.tt.league.champion.model.Matches;
import com.tt.league.champion.model.Participants;

public interface IMatchesService {

	/**
	 * @param noOfMatchesPerRound 
	 * @param noOfRounds 
	 * @param noOfTotalMatches 
	 * @param participants 
	 * @param league 
	 */
	void createMatches(List<Participants> participants, int noOfTotalMatches, int noOfRounds, int noOfMatchesPerRound, League league);

	/**
	 * @return
	 */
	List<Matches> findAllMatches();

	/**
	 * Update match winner and results
	 * @param matches
	 * @return
	 */
	String updateMatchWinnerAndResult(Matches matches);
	
}
