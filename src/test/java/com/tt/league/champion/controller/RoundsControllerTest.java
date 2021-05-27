package com.tt.league.champion.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
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

import com.tt.league.champion.model.Matches;
import com.tt.league.champion.model.Participants;
import com.tt.league.champion.model.Round;
import com.tt.league.champion.model.Round.RoundStatus;
import com.tt.league.champion.service.IRoundsService;
import com.tt.league.champion.utils.MessageUtils;

@WebMvcTest(controllers = RoundsController.class)
@ActiveProfiles("test")
public class RoundsControllerTest {

	@Autowired
    private MockMvc mockMvc;

    @MockBean
    private IRoundsService roundsService;
    
    private Participants player1;
	private Participants player2;
	private Round round;
    
    private List<Matches> matchesList=new ArrayList<>();
    private Matches match;
    private List<Round> roundList=new ArrayList<>();
    
	
    @BeforeEach
	public void setup()
	{
    	match=new Matches((long) 1, player1, player2, round, LocalDate.now(), "2-1,3-2", null, null);
    	
    	player1=new Participants((long) 1, "Rahul", "R@mail.com", 1, "+919754258264");
    	player2=new Participants((long) 2, "Mark", "m@mail.com", 2, "+919754258164");
    	matchesList.add(match);
    	round=new Round((long)1, "Round 1", 1, RoundStatus.NEW, matchesList);
    	roundList.add(round);
	}
    
    @Test
    @Tag("testcloseRoundSuccess")
    void testcloseRoundSuccess() throws Exception {

    	Mockito.when(roundsService.closeRound((long) 1)).thenReturn(MessageUtils.ROUND_CLOSED_SUCCESSFULLY);
    	mockMvc.perform(put("/api/rounds/1").contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(content().string(MessageUtils.ROUND_CLOSED_SUCCESSFULLY));
    }
    
    @Test
    @Tag("testFindAllMatchesByRoundNo")
    void testFindAllMatchesByRoundNo() throws Exception {
    	
    	Mockito.when(roundsService.findAllMatchesByRoundNo(1)).thenReturn(roundList);
    	mockMvc.perform(get("/api/rounds/1").contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON));
            //.andExpect(jsonPath("$", hasSize(2)));
            
    }
}
