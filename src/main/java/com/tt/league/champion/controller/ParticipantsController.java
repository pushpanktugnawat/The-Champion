package com.tt.league.champion.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.tt.league.champion.model.Participants;
import com.tt.league.champion.service.IParticipantsService;

@RestController
@RequestMapping(path="/api/participants",produces=MediaType.APPLICATION_JSON_VALUE,consumes=MediaType.APPLICATION_JSON_VALUE)
public class ParticipantsController {

	private Logger logger=LoggerFactory.getLogger(ParticipantsController.class);

	@Autowired
	private IParticipantsService participantsService;

	/**
	 * Submit a participant request
	 * @param participants
	 * @return
	 */
	@PostMapping
	public ResponseEntity<String> createParticipants(@RequestBody Participants participants)
	{
		logger.info("@method createParticipants");
		try {
			return ResponseEntity.ok(participantsService.createParticipants(participants));
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
		} 
	}

	/**
	 * Get a list of all participants
	 * @return
	 */
	@GetMapping
	public ResponseEntity<List<Participants>> findAllParticipants()
	{
		logger.info("@method findAllParticipants");

		return ResponseEntity.ok(participantsService.findAllParticipants());
	}


}
