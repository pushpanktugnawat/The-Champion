package com.tt.league.champion.service;

import com.tt.league.champion.model.League;

public interface ILeagueService {

	/**
	 * create League
	 * @param league
	 * @return
	 */
	String createLeague(League league);

	/**
	 * @param league
	 * @return
	 */
	String updateLeagueChampion(League league);

}
