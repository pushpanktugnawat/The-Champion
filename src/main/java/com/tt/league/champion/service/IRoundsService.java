package com.tt.league.champion.service;

import java.util.List;

import com.tt.league.champion.model.Round;

public interface IRoundsService {

	/**
	 * @param roundNo
	 * @return
	 */
	List<Round> findAllMatchesByRoundNo(int roundNo);

	/**
	 * @param roundId
	 * @return
	 */
	String closeRound(Long roundId);

	/**
	 * @param round
	 * @return
	 */
	Round createRound(Round round);

}
