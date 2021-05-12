package com.tt.league.champion.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import com.tt.league.champion.exceptions.UnexpectedServerErrorException;
import com.tt.league.champion.model.Group;
import com.tt.league.champion.model.Participants;
import com.tt.league.champion.repository.IGroupRepository;
import com.tt.league.champion.service.impl.GroupsServiceImpl;
import com.tt.league.champion.utils.MessageUtils;

@ExtendWith(MockitoExtension.class)
public class GroupsServiceImplTest {

	@InjectMocks
	private GroupsServiceImpl groupsServiceImpl;
	
	@Mock
	private IGroupRepository groupRepository;
	
	@Mock
	private IParticipantsService participantsService;
	
	@Mock
	private IMatchesService matchesService;
	
	private Participants player1;
	private Participants player2;
	private List<Participants> participantsList=new ArrayList<>();
   
    @BeforeEach
	public void setup()
	{
    	player1=new Participants((long) 1, "Rahul", "R@mail.com", 1, "+919754258264");
    	player2=new Participants((long) 2, "Mark", "m@mail.com", 2, "+919754258164");
    	
    	participantsList.add(player1);
    	participantsList.add(player2);
	}
    
    @Test
	public void createGroupsSuccessTest() 
	{
    	Mockito.when(participantsService.findAllParticipants()).thenReturn(participantsList);
    	groupsServiceImpl.createGroups();
	}
    
    @Test
	public void closeRoundUnableToFindRoundTest() 
	{
    	Mockito.when(participantsService.findAllParticipants()).thenReturn(null);
    	Assertions.assertThrows(UnexpectedServerErrorException.class, () -> {
    		groupsServiceImpl.createGroups();
    	  });
    }
    
    

}
