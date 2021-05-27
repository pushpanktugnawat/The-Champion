package com.tt.league.champion.service;

import static org.junit.jupiter.api.Assertions.assertEquals;

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

import com.tt.league.champion.model.Matches;
import com.tt.league.champion.model.Participants;
import com.tt.league.champion.model.Round;
import com.tt.league.champion.model.Round.RoundStatus;
import com.tt.league.champion.repository.IMatchesRepository;
import com.tt.league.champion.service.impl.MatchesServiceImpl;
import com.tt.league.champion.utils.MessageUtils;

@ExtendWith(MockitoExtension.class)
public class MatchesServiceImplTest {

	@InjectMocks
	private MatchesServiceImpl matchesServiceImpl;
	
	@Mock
	private IMatchesRepository matchesRepository;
	
	@Mock
	private IRoundsService roundsService;

	
	private Participants player1;
	private Participants player2;
	private Round round;
    private List<Participants> participantsList=new ArrayList<>();
    
    private List<Matches> matchesList=new ArrayList<>();
    private Matches match;
    
   
    @BeforeEach
	public void setup()
	{
    	round=new Round((long) 1, "Round 1", 1, RoundStatus.NEW,null);
    	match=new Matches((long) 1, player1, player2, round, LocalDate.now(), "2-1,3-2", null, null);
    	
    	player1=new Participants((long) 1, "Rahul", "R@mail.com", 1, "+919754258264");
    	player2=new Participants((long) 2, "Mark", "m@mail.com", 2, "+919754258164");
    	
    	participantsList.add(player1);
    	participantsList.add(player2);
    	matchesList.add(match);
	}
    
    @Test
   	public void findAllMatchesSuccessTest() 
   	{
       	Mockito.when(matchesRepository.findAll()).thenReturn(this.matchesList);

       	List<Matches> matches=matchesServiceImpl.findAllMatches();
       	Assertions.assertEquals(matches.size(), matchesList.size());
   	}
    
    @Test
	public void createMatchesSuccessTest() 
	{
    	matchesServiceImpl.createMatches(participantsList, 1, 1, 1, null);
	}
    
    @Test
    @Tag("testUpdateMatchWinnerAndResult")
  	public void testUpdateMatchWinnerAndResult() {
    	match.setWinner(player1);
    	Mockito.when(matchesRepository.findById(match.getMatchId())).thenReturn(Optional.of(match));
    	Mockito.when(matchesRepository.save(match)).thenReturn(match);
    	Mockito.when(roundsService.closeRound(match.getRound().getRoundId())).thenReturn("match");
    	String updateLeagueChampion = matchesServiceImpl.updateMatchWinnerAndResult(match);
    	assertEquals(MessageUtils.MATCH_WINNER_UPDATED_SUCCESSFULLY, updateLeagueChampion);
    }
}
