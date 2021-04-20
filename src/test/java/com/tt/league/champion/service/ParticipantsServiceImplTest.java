package com.tt.league.champion.service;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import com.tt.league.champion.model.Participants;
import com.tt.league.champion.repository.IParticipantsRepository;
import com.tt.league.champion.service.impl.ParticipantsServiceImpl;

@ExtendWith(MockitoExtension.class)
public class ParticipantsServiceImplTest {

	@InjectMocks
	private ParticipantsServiceImpl participantsServiceImpl;
	
	@Mock
	private IParticipantsRepository participantsRepository;
	
	private Participants participants;
    private List<Participants> participantsList=new ArrayList<>();
    
   
    @BeforeEach
	public void setup()
	{
    	participants=new Participants((long) 1, "Rahul", "R@mail.com", 1, "+919754258264");
    	participantsList.add(participants);
	}
    
    @Test
	public void saveParticipantsSuccessTest() 
	{
    	participantsServiceImpl.createParticipants(participants);
	}
    
    @Test
   	public void findAllParticipantsSuccessTest() 
   	{
       	Mockito.when(participantsRepository.findAll()).thenReturn(this.participantsList);

       	List<Participants> participants1=participantsServiceImpl.findAllParticipants();
       	Assertions.assertEquals(participants1.size(), participantsList.size());
   	}
}
