package com.tt.league.champion.model;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name="tt_league")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class League implements Serializable{

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Long leagueId;
	
	private String name;
	
	@ManyToOne(fetch=FetchType.LAZY)
	private Participants winner;
	
	@Transient
	private List<Participants> participants;
	
	private LocalDate startDate;
}
