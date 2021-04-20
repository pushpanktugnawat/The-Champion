package com.tt.league.champion.service;

import java.util.List;

import com.tt.league.champion.model.Participants;

public interface IParticipantsService {

	/**
	 * @param participants
	 * @return
	 */
	String createParticipants(Participants participants);

	/**
	 * @return
	 */
	List<Participants> findAllParticipants();

}
