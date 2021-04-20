package com.tt.league.champion.model;

import java.io.Serializable;
import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name="tt_matches")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler","round"})
public class Matches implements Serializable{

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="tt_match_id")
	private Long matchId;
	
	@ManyToOne(fetch=FetchType.LAZY)
	private Participants player1;
	
	@ManyToOne(fetch=FetchType.LAZY)
	private Participants player2;
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="tt_round")
	@JsonIgnore
	private Round round;
	
	private LocalDate dueDate;
	
	private String result;
	
	@ManyToOne(fetch=FetchType.LAZY)
	private Participants winner;
	
	@ManyToOne(fetch=FetchType.LAZY)
	private League league;
}
