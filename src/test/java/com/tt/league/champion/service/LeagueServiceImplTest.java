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
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;

import com.tt.league.champion.exceptions.NoParticipantFound;
import com.tt.league.champion.model.League;
import com.tt.league.champion.model.Participants;
import com.tt.league.champion.repository.ILeagueRepository;
import com.tt.league.champion.service.impl.LeagueServiceImpl;
import com.tt.league.champion.utils.MessageUtils;

@ExtendWith(MockitoExtension.class)
//@MockitoSettings(strictness = Strictness.LENIENT)
public class LeagueServiceImplTest {
	
	@InjectMocks
	private LeagueServiceImpl leagueServiceImpl;

	@Mock
	private ILeagueRepository leagueRepository;
	
	@Mock
	private IMatchesService matchService;
	
	private League league;
	private Participants participants1;
	private List<Participants> participants=new ArrayList<>();
	
    
    
	@BeforeEach
	public void setup()
	{
    	participants1=new Participants((long) 1, "Rahul", "R@mail.com", 1, "+919754258264");
    	participants.add(participants1);
    	participants.add(participants1);
    	league = new League((long) 1, "CPL",participants1,participants, LocalDate.now());
    }
    
    @Test
    @Tag("testUpdateLeagueChampion")
  	public void testUpdateLeagueChampion() {
    	Mockito.when(leagueRepository.findById(league.getLeagueId())).thenReturn(Optional.of(league));
    	league.setWinner(participants1);
    	Mockito.when(leagueRepository.save(league)).thenReturn(league);
    	String updateLeagueChampion = leagueServiceImpl.updateLeagueChampion(league);
    	assertEquals(MessageUtils.CHAMPION_UPDATED_SUCCESSFULLY, updateLeagueChampion);
    }
    
    @Test
    @Tag("testUpdateLeagueChampionFailure")
  	public void testUpdateLeagueChampionFailure() {
    	Mockito.when(leagueRepository.findById(league.getLeagueId())).thenReturn(Optional.empty());
    	String updateLeagueChampion = leagueServiceImpl.updateLeagueChampion(league);
    	assertEquals(MessageUtils.NO_LEAGUE_FOUND, updateLeagueChampion);
    }
    
    @Test
    @Tag("testCreateLeague")
  	public void testCreateLeague() {
    	//Mockito.when(leagueRepository.save(league)).thenReturn(league);
    	league.setLeagueId(null);
    	String updateLeagueChampion = leagueServiceImpl.createLeague(league);
    	assertEquals(MessageUtils.LEAGUE_CREATED_SUCCESSFULLY, updateLeagueChampion);
    }
    
    @Test
    @Tag("testCreateLeagueNoParticipantFoundFailure")
    public void testCreateLeagueNoParticipantFoundFailure() {
    	league.setParticipants(null);
    	Assertions.assertThrows(NoParticipantFound.class, () -> {
    		leagueServiceImpl.createLeague(league);
    	  });
    }
    
    @Test
    @Tag("testCreateLeagueSizeOfParticipantFailure")
  	public void testCreateLeagueSizeOfParticipantFailure() {
    	while(participants.size()<13) {
        	participants.add(participants1);
    	}
		league.setParticipants(participants);
    	System.out.println(league.getParticipants().size());
    	Assertions.assertThrows(NoParticipantFound.class, () -> {
    		leagueServiceImpl.createLeague(league);
    	  });
    }
    
}
