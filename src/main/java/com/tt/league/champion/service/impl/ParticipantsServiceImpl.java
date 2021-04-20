package com.tt.league.champion.service.impl;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tt.league.champion.model.Participants;
import com.tt.league.champion.repository.IParticipantsRepository;
import com.tt.league.champion.service.IParticipantsService;
import com.tt.league.champion.utils.MessageUtils;

@Service
public class ParticipantsServiceImpl implements IParticipantsService{

	private Logger logger=LoggerFactory.getLogger(ParticipantsServiceImpl.class);
	
	@Autowired
	private IParticipantsRepository participantsRepository;
	
	
	/* (non-Javadoc)
	 * @see com.tt.league.champion.service.IParticipantsService#createParticipants(com.tt.league.champion.model.Participants)
	 */
	@Override
	public String createParticipants(Participants participants) {
		
		logger.info("@method createParticipants");
		participantsRepository.save(participants);
		return MessageUtils.PARTICIPANTS_CREATED_SUCCESSFULLY;
	}

	/* (non-Javadoc)
	 * @see com.tt.league.champion.service.IParticipantsService#findAllParticipants()
	 */
	@Override
	public List<Participants> findAllParticipants() {
	
		logger.info("@method findAllParticipants");
		return participantsRepository.findAll();
	}

}
