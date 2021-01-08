package com.revature.beans;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table
public class InfoRequest {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name="recipient")
	private Person recipient;
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name="sender")
	private Person sender;
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "pitch_id")
	private Pitch pitch;
	
	private String question;
	private String answer;
	
	public InfoRequest() {
		id = 0;
		recipient = new Person();
		sender = new Person();
		pitch = new Pitch();
		question = "";
		answer = "";
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Person getRecipient() {
		return recipient;
	}

	public void setRecipient(Person recipient) {
		this.recipient = recipient;
	}

	public Person getSender() {
		return sender;
	}

	public void setSender(Person sender) {
		this.sender = sender;
	}

	public Pitch getPitch() {
		return pitch;
	}

	public void setPitch(Pitch pitch) {
		this.pitch = pitch;
	}

	public String getQuestion() {
		return question;
	}

	public void setQuestion(String question) {
		this.question = question;
	}

	public String getAnswer() {
		return answer;
	}

	public void setAnswer(String answer) {
		this.answer = answer;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((answer == null) ? 0 : answer.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((pitch == null) ? 0 : pitch.hashCode());
		result = prime * result + ((question == null) ? 0 : question.hashCode());
		result = prime * result + ((recipient == null) ? 0 : recipient.hashCode());
		result = prime * result + ((sender == null) ? 0 : sender.hashCode());
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
		InfoRequest other = (InfoRequest) obj;
		if (answer == null) {
			if (other.answer != null)
				return false;
		} else if (!answer.equals(other.answer))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (pitch == null) {
			if (other.pitch != null)
				return false;
		} else if (!pitch.equals(other.pitch))
			return false;
		if (question == null) {
			if (other.question != null)
				return false;
		} else if (!question.equals(other.question))
			return false;
		if (recipient == null) {
			if (other.recipient != null)
				return false;
		} else if (!recipient.equals(other.recipient))
			return false;
		if (sender == null) {
			if (other.sender != null)
				return false;
		} else if (!sender.equals(other.sender))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "InfoRequest id:" + id + " has recipient: " + recipient + " and sender: " + sender + " from the pitch " + pitch.getStory_title()
				+ ".\nAsking the  question: " + question + 
				"\nAnswer=" + answer + ".";
	}
	
	
}
