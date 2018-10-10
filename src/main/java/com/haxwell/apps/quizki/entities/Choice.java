package com.haxwell.apps.quizki.entities;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Choice {
	
	@Id
	@GeneratedValue(strategy= GenerationType.IDENTITY)
	protected long id;
	
	private String text;
	private int sequence;
	private boolean isCorrect;
	
	public Choice(long id, String text, int sequence) {
//		super();
		this.id = id;
		this.text = text;
		this.sequence = sequence;
	}

	public Choice(long id, String text, int sequence, boolean isCorrect) {
//		super();
		this.id = id;
		this.text = text;
		this.sequence = sequence;
		this.isCorrect = isCorrect;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public int getSequence() {
		return sequence;
	}

	public void setSequence(int sequence) {
		this.sequence = sequence;
	}

	public boolean isCorrect() {
		return isCorrect;
	}

	public void setCorrect(boolean isCorrect) {
		this.isCorrect = isCorrect;
	}

	@Override
	public String toString() {
		return "Choice [id=" + id + ", text=" + text + ", sequence=" + sequence + ", isCorrect=" + isCorrect + "]";
	}

}
