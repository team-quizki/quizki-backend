package com.haxwell.apps.quizki.entities;


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

import com.fasterxml.jackson.annotation.JsonIgnore;


@Entity
public class Topic {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    public void setId(Long l) {
    	id = l;
    }
    
    public Long getId() {
        return id;
    }
    
    private String text;
    
    public String getText() {
        return text;
    }
    
    public void setText(String str) { 
    	this.text = str;
    }
    
    @JsonIgnore
    @ManyToMany(fetch = FetchType.LAZY,
    			cascade = {CascadeType.PERSIST, CascadeType.MERGE},
    			mappedBy = "topics")
    private Set<Question> questions = new HashSet<Question>();
   
    
    public Set<Question> getQuestions() {
		return questions;
	}

	public void setQuestions(Set<Question> questions) {
		this.questions = questions;
	}
	
	public void addQuestion(Question question) {
		this.questions.add(question);
	}

	public void removeQuestion(Question question) {
		this.questions.remove(question);
	}
	
	
	public String toString() {
    	return id + " " + text;
    }

    public Topic(String str) {
        this.text = str;
    }

    //used exclusively by JPA
    protected Topic() { 
    	
    }
    
    public Topic(long id, String text) {
    	this.text = text;
    	this.id = id;
    	this.questions = new HashSet<Question>();
    }
    
    @Override
    public int hashCode() {
        return id == null ? 0 : id.hashCode();
    }    
    
    @Override
    public boolean equals(Object o) {
    	boolean rtn = false;
    	
    	if (o instanceof Topic) {
    		if(this.getId() == null)
    			return false;
    		rtn = ((Topic)o).getId().equals(this.getId());
    	}
    	
    	return rtn;
    }

	
}