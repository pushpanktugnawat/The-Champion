package com.tt.league.champion.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tt.league.champion.model.League;
import com.tt.league.champion.model.Participants;
import com.tt.league.champion.service.ILeagueService;
import com.tt.league.champion.utils.MessageUtils;

@WebMvcTest(controllers = LeagueController.class)
@ActiveProfiles("test")
public class LeagueControllerTest {

	@Autowired
    private MockMvc mockMvc;

	@MockBean
	private ILeagueService leagueService;
	
	private League league;
	private Participants participants1;
	private Participants participants2;
	private List<Participants> participantsList=new ArrayList<>();
	
    
    @Autowired
	private ObjectMapper objectMapper;
    
	@BeforeEach
	public void setup()
	{
    	participants1=new Participants((long) 1, "Rahul", "R@mail.com", 1, "+919754258264");
    	participants2=new Participants((long) 2, "Golu", "G@mail.com", 1, "+919754258264");
    	participantsList.add(participants1);
    	participantsList.add(participants2);
    	league = new League((long) 1, "Moiz",participants1,participantsList, LocalDate.now());
	}
	
	@Test
    @Tag("testcreateLeague")
    void testcreateLeague() throws Exception {

    	Mockito.when(leagueService.createLeague(this.league)).thenReturn(MessageUtils.LEAGUE_CREATED_SUCCESSFULLY);
    	mockMvc.perform(post("/api/league").contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(this.league))
                .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(content().string(MessageUtils.LEAGUE_CREATED_SUCCESSFULLY));
    }
	
	@Test
    @Tag("testUpdateLeagueChampion")
    void testUpdateLeagueChampion() throws Exception {

    	Mockito.when(leagueService.updateLeagueChampion(this.league)).thenReturn("League Updated Successfully");
    	mockMvc.perform(put("/api/league").contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(this.league))
                .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(content().string("League Updated Successfully"));
    }
	
	
	
	
	
}
