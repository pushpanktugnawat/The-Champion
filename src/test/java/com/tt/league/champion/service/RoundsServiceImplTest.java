package com.tt.league.champion.service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import com.tt.league.champion.exceptions.NoParticipantFound;
import com.tt.league.champion.model.Matches;
import com.tt.league.champion.model.Participants;
import com.tt.league.champion.model.Round;
import com.tt.league.champion.model.Round.RoundStatus;
import com.tt.league.champion.repository.IRoundRepository;
import com.tt.league.champion.service.impl.RoundsServiceImpl;
import com.tt.league.champion.utils.MessageUtils;

@ExtendWith(MockitoExtension.class)
public class RoundsServiceImplTest {

	@InjectMocks
	private RoundsServiceImpl roundServiceImpl;
	
	@Mock
	private IRoundRepository roundRepository;
	
	private Participants player1;
	private Participants player2;
	private Round round;
    
    private List<Matches> matchesList=new ArrayList<>();
    private Matches match,match1;
    private List<Round> roundList=new ArrayList<>();
    
	
    @BeforeEach
	public void setup()
	{
    	
    	player1=new Participants((long) 1, "Rahul", "R@mail.com", 1, "+919754258264");
    	player2=new Participants((long) 2, "Mark", "m@mail.com", 2, "+919754258164");
    	
    	match=new Matches((long) 1, player1, player2, round, LocalDate.now(), "2-1,3-2", player1, null);
    	matchesList.add(match);
    	round=new Round((long)1, "Round 1", 1, RoundStatus.NEW, matchesList);
    	
    	roundList.add(round);
	}
    
    @Test
   	public void findAllMatchesByRoundNoSuccessTest() 
   	{
       	Mockito.when(roundRepository.findByRoundNo(1)).thenReturn(this.roundList);

       	List<Round> rounds=roundServiceImpl.findAllMatchesByRoundNo(1);
       	Assertions.assertEquals(rounds.size(), roundList.size());
   	}
    
    @Test
	public void closeRoundSuccessTest() 
	{
    	Mockito.when(roundRepository.findById(round.getRoundId())).thenReturn(Optional.of(round));
    	round.setRoundStatus(RoundStatus.CLOSE);
    	Mockito.when(roundRepository.save(round)).thenReturn(round);
    	String closeRoundStatus = roundServiceImpl.closeRound(round.getRoundId());
    	Assertions.assertEquals(MessageUtils.ROUND_CLOSED_SUCCESSFULLY, closeRoundStatus);
    }
    
    @Test
	public void closeRoundUnableToFindRoundTest() 
	{
    	Mockito.when(roundRepository.findById(round.getRoundId())).thenReturn(Optional.empty());
    	String roundNotFound = roundServiceImpl.closeRound(round.getRoundId());
    	
    	String closeRoundStatus = roundServiceImpl.closeRound(round.getRoundId());
    	Assertions.assertEquals(MessageUtils.UNABLE_TO_FIND_ROUND, roundNotFound);
    }
    
   
}
