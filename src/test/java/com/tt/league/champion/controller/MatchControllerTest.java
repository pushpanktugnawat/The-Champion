package com.tt.league.champion.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
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
import com.tt.league.champion.model.Matches;
import com.tt.league.champion.model.Participants;
import com.tt.league.champion.service.IMatchesService;
import com.tt.league.champion.utils.MessageUtils;

@WebMvcTest(controllers = MatchController.class)
@ActiveProfiles("test")
public class MatchControllerTest {

	@Autowired
    private MockMvc mockMvc;

    @MockBean
    private IMatchesService matchesService;
    
    @Autowired
	private ObjectMapper objectMapper;
    
    private Matches match;
    private League league;
	private Participants participants1;
	private Participants participants2;
	private List<Participants> participantsList=new ArrayList<>();
    
    @BeforeEach
	public void setup()
	{
    	participants1=new Participants((long) 1, "Rahul", "R@mail.com", 1, "+919754258264");
    	participants2=new Participants((long) 2, "Golu", "G@mail.com", 1, "+919754258264");
    	participantsList.add(participants1);
    	participantsList.add(participants2);
    	league = new League((long) 1, "Moiz",participants1,participantsList, LocalDate.now());
    	match = new Matches((long) 1,participants1,participants2,null,LocalDate.now(),"Win",participants1,league);
	}
    
    @Test
    @Tag("testFindAllMatchesSuccess")
    void testFindAllMatchesSuccess() throws Exception {

    	mockMvc.perform(get("/api/match").contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON));
    }
    
    @Test
    @Tag("testUpdateMatch")
    void testUpdateMatch() throws Exception {
    	Mockito.when(matchesService.updateMatchWinnerAndResult(this.match)).thenReturn(MessageUtils.LEAGUE_CREATED_SUCCESSFULLY);
    	mockMvc.perform(post("/api/match/updateMatch").contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(this.match))
                .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(content().string(MessageUtils.MATCH_WINNER_UPDATED_SUCCESSFULLY));
    }
    
}
