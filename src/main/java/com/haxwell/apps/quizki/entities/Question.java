package com.haxwell.apps.quizki.entities;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

@Entity
public class Question {

	//TODO: Add validation including a check for forbidden characters to prevent script attacks
	//	if a validation method doesn't exist use if(String s != HtmlUtils.htmlEscape(s)) then Exception
	
	@Id
	@GeneratedValue(strategy= GenerationType.AUTO)
	protected long id;
	
	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	@JoinColumn(name = "user_id", nullable = false)
	private User user;
	
	private String description;
	private String text;
	private int difficulty;
	private int questionType;
	
	@ManyToMany(fetch = FetchType.LAZY,
			cascade = {CascadeType.MERGE, CascadeType.PERSIST})	
	@JoinTable(name = "question_reference",
				joinColumns = {@JoinColumn(name = "question_id", referencedColumnName = "id")},
				inverseJoinColumns = {@JoinColumn(name = "reference_id", referencedColumnName = "id")})
	private Set<Reference> references = new HashSet<Reference>();
	
	@ManyToMany(fetch = FetchType.LAZY, 
			cascade = {CascadeType.MERGE, CascadeType.PERSIST})
	@JoinTable(name = "question_topic",
				joinColumns = {@JoinColumn(name = "question_id", referencedColumnName = "id")},
				inverseJoinColumns = {@JoinColumn(name = "topic_id", referencedColumnName = "id")})
	private Set<Topic> topics = new HashSet<Topic>();
	
	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "question")
	private Set<Choice> choices = new HashSet<Choice>();


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


	public int getDifficulty() {
		return difficulty;
	}


	public void setDifficulty(int difficulty) {
		this.difficulty = difficulty;
	}


	public int getQuestionType() {
		return questionType;
	}


	public void setQuestionType(int questionType) {
		this.questionType = questionType;
	}


	public Set<Reference> getReferences() {
		return references;
	}


	public void setReferences(Set<Reference> references) {
		this.references = references;
	}

	
	public void addReference(Reference ref) {
		references.add(ref);
		ref.getQuestions().add(this);
	}
	
	public void removeReference(Reference ref) {
		references.remove(ref);
		ref.getQuestions().remove(this);
	}

	public Set<Topic> getTopics() {
		return topics;
	}


	public void setTopics(Set<Topic> topics) {
		this.topics = topics;
	}
	
	public void addTopic(Topic topic) {
		topics.add(topic);
		topic.getQuestions().add(this);
	}
	
	public void removeTopic(Topic topic) {
		topics.remove(topic);
		topic.getQuestions().remove(this);
	}


	public Set<Choice> getChoices() {
		return choices;
	}


	public void setChoices(Set<Choice> choices) {
		this.choices = choices;
	}

	public void addChoice(Choice choice) {
		this.choices.add(choice);
		choice.setQuestion(this);
	}
	
	public void removeChoice(Choice choice) {
		this.choices.remove(choice);
	}

	@Override
	public String toString() {
		return "Question [id=" + id + ", user=" + user + ", description=" + description + ", text=" + text
				+ ", difficulty=" + difficulty + ", questionType=" + questionType + ", references=" + references
				+ ", topics=" + topics + ", choices=" + choices + "]";
	}


	
	
	
	
	
	
}
