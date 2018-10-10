package com.haxwell.apps.quizki.entities;

import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Question {

	@Id
	@GeneratedValue(strategy= GenerationType.IDENTITY)
	protected long id;
	
	private User user;
	private String description;
	private String text;
	private String difficulty;
	private String questionType;
	
	private Set<Reference> references;
	
	
	private Set<Topic> topics;
	
	
	private Set<Choice> choices;


	public Question() {
		
	}


	public long getId() {
		return id;
	}


	public void setId(long id) {
		this.id = id;
	}


	public User getUser() {
		return user;
	}


	public void setUser(User user) {
		this.user = user;
	}


	public String getDescription() {
		return description;
	}


	public void setDescription(String description) {
		this.description = description;
	}


	public String getText() {
		return text;
	}


	public void setText(String text) {
		this.text = text;
	}


	public String getDifficulty() {
		return difficulty;
	}


	public void setDifficulty(String difficulty) {
		this.difficulty = difficulty;
	}


	public String getQuestionType() {
		return questionType;
	}


	public void setQuestionType(String questionType) {
		this.questionType = questionType;
	}


	public Set<Reference> getReferences() {
		return references;
	}


	public void setReferences(Set<Reference> references) {
		this.references = references;
	}


	public Set<Topic> getTopics() {
		return topics;
	}


	public void setTopics(Set<Topic> topics) {
		this.topics = topics;
	}


	public Set<Choice> getChoices() {
		return choices;
	}


	public void setChoices(Set<Choice> choices) {
		this.choices = choices;
	}


	@Override
	public String toString() {
		return "Question [id=" + id + ", user=" + user + ", description=" + description + ", text=" + text
				+ ", difficulty=" + difficulty + ", questionType=" + questionType + ", references=" + references
				+ ", topics=" + topics + ", choices=" + choices + "]";
	}
	
	
	
	
	
}
