package com.tt.league.champion.service.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import com.tt.league.champion.exceptions.UnexpectedServerErrorException;
import com.tt.league.champion.model.Group;
import com.tt.league.champion.model.Participants;
import com.tt.league.champion.repository.IGroupRepository;
import com.tt.league.champion.service.IGroupsService;
import com.tt.league.champion.service.IMatchesService;
import com.tt.league.champion.service.IParticipantsService;
import com.tt.league.champion.utils.CommonUtils;

@Service
public class GroupsServiceImpl implements IGroupsService{

	private Logger logger=LoggerFactory.getLogger(GroupsServiceImpl.class);

	@Autowired
	private IParticipantsService participantsService;

	@Autowired
	private IGroupRepository groupRepository;
	
	@Autowired
	private IMatchesService matchesService;

	/* (non-Javadoc)
	 * @see com.tt.league.champion.service.IGroupsService#createGroups()
	 */
	@Override
	public List<Group> createGroups() {

		logger.info("@method createGroups");

		List<Participants> participants=participantsService.findAllParticipants();

		if(!CollectionUtils.isEmpty(participants) && participants.size()>1)
		{
			Collections.shuffle(participants,new Random(5));
			int size=participants.size();
			
			int noOfTotalMatches=CommonUtils.calculateNoOfMatchesBasedOnParticipants(size);
			
			int noOfRounds=CommonUtils.calculateNoOfRounds(size);
					
			int noOfMatchesPerRound=CommonUtils.calculateNoOfMatchesPerRound(noOfTotalMatches,noOfRounds);
			
			List<Group> groups=new ArrayList<>();
			int i=1;
			for (int index = 0; index < participants.size(); index +=noOfMatchesPerRound) 
			{
				int temp=index+noOfMatchesPerRound>=participants.size()?participants.size():index+noOfMatchesPerRound;
				
				List<Participants> listParticipantsGroup1=participants.subList(index, temp);
				groups.add(createGroup(listParticipantsGroup1,"Group "+i));
				i++;
			}
			
			createMatches(participants,noOfTotalMatches,noOfRounds,noOfMatchesPerRound);
			
			return groups;

		}else{
			throw new UnexpectedServerErrorException("No Participants found");
		}
	}

	private void createMatches(List<Participants> participants, int noOfTotalMatches, int noOfRounds, int noOfMatchesPerRound) {
		
		logger.info("@method createMatches");
		matchesService.createMatches(participants,noOfTotalMatches,noOfRounds,noOfMatchesPerRound,null);
		
	}

	/**
	 * @param listParticipantsGroup
	 * @param name
	 * @return
	 */
	private Group createGroup(List<Participants> listParticipantsGroup, String name) {

		Group group=new Group();
		group.setGroupParticipant(listParticipantsGroup);
		group.setName(name);

		return groupRepository.save(group);
	}

}
