package com.tt.league.champion.model;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name="tt_group")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Group implements Serializable{

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="tt_group_id")
	private Long groupid;
	
	private String name;
	
	@ManyToMany(targetEntity=Participants.class, fetch=FetchType.LAZY)
	@JoinTable(name="group_participants",joinColumns=@JoinColumn(name="tt_group_id"),inverseJoinColumns=@JoinColumn(name="participant_id"))
	private List<Participants> groupParticipant; 
}
