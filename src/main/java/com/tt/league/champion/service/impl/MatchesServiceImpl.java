package com.tt.league.champion.service.impl;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tt.league.champion.exceptions.UnexpectedServerErrorException;
import com.tt.league.champion.model.League;
import com.tt.league.champion.model.Matches;
import com.tt.league.champion.model.Participants;
import com.tt.league.champion.model.Round;
import com.tt.league.champion.model.Round.RoundStatus;
import com.tt.league.champion.repository.IMatchesRepository;
import com.tt.league.champion.service.IMatchesService;
import com.tt.league.champion.service.IRoundsService;
import com.tt.league.champion.utils.MessageUtils;


@Service
public class MatchesServiceImpl implements IMatchesService{

	private Logger logger=LoggerFactory.getLogger(MatchesServiceImpl.class);

	@Autowired
	private IMatchesRepository matchesRepository;

	@Autowired
	private IRoundsService roundService;

	/* (non-Javadoc)
	 * @see com.tt.league.champion.service.IMatchesService#createMatches(java.util.List, int, int, int)
	 */
	@Override
	public void createMatches(List<Participants> participants, int noOfTotalMatches, int noOfRounds,
			int noOfMatchesPerRound,League league) 
	{	
		logger.info("@method createMatches");
		List<Round> rounds=createRounds(noOfRounds);
		createMatchFromRound(participants,rounds,league);
	}
	
	/**
	 * @param noOfRounds
	 * @return
	 */
	private List<Round> createRounds(int noOfRounds) {

		List<Round> rounds=new ArrayList<>();
		for(int i=1;i<=noOfRounds;i++)
		{
			Round round=new Round();
			round.setName("Round "+i);
			round.setRoundNo(i);
			round.setRoundStatus(RoundStatus.NEW);
			rounds.add(roundService.createRound(round));
		}
		return rounds;
	}

	/**
	 * @param participants
	 * @param league 
	 * @param group
	 */
	private void createMatchFromRound(List<Participants> participants,List<Round> rounds, League league) {

		logger.info("@method createMatchFromGroup");
		LocalDate localDate=LocalDate.now();
		if(league!=null)
		{
			localDate=league.getStartDate();
		}
		int matchCounter=0;
		int temp=0;
		
		for(int i=0;i<participants.size()-1;i++)
		{
			for(int j=i+1;j<participants.size();j++)
			{
				if(temp==rounds.size())
				{
					temp=0;
				}
				//3 matches / day
				if(matchCounter==3)
				{
					matchCounter=0;
					localDate=localDate.plusDays(1);
				}
				createMatch(participants.get(i),participants.get(j),rounds.get(temp),localDate,league);
				temp++;
				matchCounter++;
			}
		}
	}
	
	
	/**
	 * @param player1
	 * @param player2
	 * @param round
	 * @param date
	 * @param league
	 * @return
	 */
	private Matches createMatch(Participants player1, Participants player2,Round round,LocalDate date,League league) {
		logger.info("@method createMatchAndRound");
		Matches matches=new Matches();
		matches.setPlayer1(player1);
		matches.setPlayer2(player2);
		matches.setRound(round);
		matches.setDueDate(date);
		matches.setLeague(league);
		return matchesRepository.save(matches);
	}

	@Override
	public List<Matches> findAllMatches() 
	{
		return matchesRepository.findAll();
	}

	@Override
	public String updateMatchWinnerAndResult(Matches matches) {
		logger.info("@method updateMatchWinnerAndResult");
		Optional<Matches> match=matchesRepository.findById(matches.getMatchId());
		
		if(match.isPresent())
		{
			Matches matchForUpdate=match.get();
			matchForUpdate.setResult(matches.getResult());
			matchForUpdate.setWinner(matches.getWinner());
			matchesRepository.save(matchForUpdate);
			try{
				roundService.closeRound(matchForUpdate.getRound().getRoundId());
			}catch(Exception ex){
				
			}
			return MessageUtils.MATCH_WINNER_UPDATED_SUCCESSFULLY;
		}else
		{
			throw new UnexpectedServerErrorException("Unable to find Match");
		}
	}
}




