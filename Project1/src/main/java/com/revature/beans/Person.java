package com.revature.beans;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;


import javax.persistence.JoinColumn;
@Entity
@Table
public class Person {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	private String username;
	private String passwd;
	@ManyToMany(fetch = FetchType.EAGER)
	@JoinTable(name="person_title",
		joinColumns=@JoinColumn(name="person_id"),
		inverseJoinColumns = @JoinColumn(name = "title_id"))
	private Set<Title> title;
	@ManyToMany(fetch = FetchType.EAGER)
	@JoinTable(name="person_committee",
		joinColumns=@JoinColumn(name="person_id"),
		inverseJoinColumns = @JoinColumn(name = "committee_id"))
	private Set<Committee> committees;
	@OneToMany(fetch = FetchType.EAGER)
	@JoinTable(name="person_pitch",
		joinColumns=@JoinColumn(name="person_id"),
		inverseJoinColumns = @JoinColumn(name = "pitch_id"))
//	@OneToMany(
//			fetch = FetchType.LAZY, 
//			mappedBy = "id" 
//			, cascade = CascadeType.ALL)
	private Set<Pitch> pitches;
	
	public Person() {
		id = 0;
		username = "";
		passwd = "";
		title = new HashSet<Title>();
		committees = new HashSet<Committee>();
		pitches = new HashSet<Pitch>();
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPasswd() {
		return passwd;
	}

	public void setPasswd(String passwd) {
		this.passwd = passwd;
	}



	public Set<Title> getTitle() {
		return title;
	}

	public void setTitle(Set<Title> title) {
		this.title = title;
	}

	public Set<Committee> getCommittees() {
		return committees;
	}

	public void setCommittees(Set<Committee> committees) {
		this.committees = committees;
	}

	public Set<Pitch> getPitches() {
		return pitches;
	}

	public void setPitches(Set<Pitch> pitches) {
		this.pitches = pitches;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((passwd == null) ? 0 : passwd.hashCode());
		result = prime * result + ((username == null) ? 0 : username.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Person other = (Person) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (passwd == null) {
			if (other.passwd != null)
				return false;
		} else if (!passwd.equals(other.passwd))
			return false;
		if (username == null) {
			if (other.username != null)
				return false;
		} else if (!username.equals(other.username))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Person: id=" + id + ", username=" + username + ", passwd=" + passwd + ".";
	}
	
	
	
}
