package com.tt.league.champion.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

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
import com.tt.league.champion.model.Participants;
import com.tt.league.champion.service.IParticipantsService;
import com.tt.league.champion.utils.MessageUtils;

@WebMvcTest(controllers = ParticipantsController.class)
@ActiveProfiles("test")
public class ParticipantsControllerTest {

	@Autowired
    private MockMvc mockMvc;

    @MockBean
    private IParticipantsService participantsService;
    
    @Autowired
	private ObjectMapper objectMapper;
	
    
    private Participants participants;
    private List<Participants> participantsList=new ArrayList<>();
    
   
    @BeforeEach
	public void setup()
	{
    	participants=new Participants((long) 1, "Rahul", "R@mail.com", 1, "+919754258264");
    	participantsList.add(participants);
	}
	
	/**
	 * Test Create Participant Success
	 *
	 * @throws Exception the exception
	 */
	@Test
    @Tag("testCreateParticipantSuccess")
    void testCreateParticipantSuccess() throws Exception {

    	Mockito.when(participantsService.createParticipants(this.participants)).thenReturn(MessageUtils.PARTICIPANTS_CREATED_SUCCESSFULLY);
    	mockMvc.perform(post("/api/participants").contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(this.participants))
                .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(content().string(MessageUtils.PARTICIPANTS_CREATED_SUCCESSFULLY));
    }

}
