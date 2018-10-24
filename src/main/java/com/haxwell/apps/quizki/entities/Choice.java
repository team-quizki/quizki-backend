package com.haxwell.apps.quizki.entities;

import com.haxwell.apps.quizki.entities.Question;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

@Entity
public class Choice {
	
	@Id
	@GeneratedValue(strategy= GenerationType.IDENTITY)
	protected long id;
	
	private String text;
	private int sequence;
	private boolean isCorrect;
	
	
	@ManyToOne (fetch = FetchType.LAZY, optional = false)
	@JoinColumn (name = "question_id", nullable = false)
	@OnDelete(action = OnDeleteAction.CASCADE)
	private Question question;
	
	
	
	public Question getQuestion() {
		return question;
	}

	public void setQuestion(Question question) {
		this.question = question;
	}

	public Choice(long id, String text, int sequence) {

		this.id = id;
		this.text = text;
		this.sequence = sequence;
	}

	public Choice(long id, String text, int sequence, boolean isCorrect) {

		this.id = id;
		this.text = text;
		this.sequence = sequence;
		this.isCorrect = isCorrect;
	}
	
	
	public Choice() {
		
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

//	public boolean isCorrect() {
//		return isCorrect;
//	}

	public void setIsCorrect(boolean isCorrect) {
		this.isCorrect = isCorrect;
	}
	
	public boolean getIsCorrect() {
		return this.isCorrect;
	}

	@Override
	public String toString() {
		return "Choice [id=" + id + ", text=" + text + ", sequence=" + sequence + ", isCorrect=" + isCorrect + "]";
	}

}
