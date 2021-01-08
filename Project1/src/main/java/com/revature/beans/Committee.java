package com.revature.beans;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table
public class Committee {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer id;
	@Column(name = "committee_name")
	private String committee_name;
	@OneToOne(fetch = FetchType.EAGER)
	@JoinColumn(name="genre_id")
	private Genre genre;
	
	public Committee() {
		id = 0;
		committee_name = "";
		genre = new Genre();
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getcommittee_name() {
		return committee_name;
	}

	public void setcommittee_name(String committee_name) {
		this.committee_name = committee_name;
	}

	public Genre getGenre() {
		return genre;
	}

	public void setGenre(Genre genre) {
		this.genre = genre;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((committee_name == null) ? 0 : committee_name.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
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
		Committee other = (Committee) obj;
		if (committee_name == null) {
			if (other.committee_name != null)
				return false;
		} else if (!committee_name.equals(other.committee_name))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Committee: id=" + id + ", Committee Name is" + committee_name + " Committee Genre is " + genre.getName();
	}
	
	
}
