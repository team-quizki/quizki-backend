package com.haxwell.apps.quizki.entities;


import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;


@Entity
public class Reference {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    public Long getId() {
        return id;
    }
    
    public void setId(Long l) {
    	this.id = l;
    }
    
    private String text;
    
    public String getText() {
        return text;
    }
    
    public void setText(String str) { 
    	this.text = str;
    }
    
    @ManyToMany(fetch = FetchType.LAZY,
			cascade = {CascadeType.PERSIST, CascadeType.MERGE},
			mappedBy = "references")
    private Set<Question> questions = new HashSet<Question>();
    
    public String toString() {
    	return id + " " + text;
    }

    public Reference(String str) {
        this.text = str;
    }

    //used exclusively by JPA
    protected Reference() { 
    	
    }
    
    @Override
    public int hashCode() {
        return id == null ? 0 : id.hashCode();
    }    
    
    @Override
    public boolean equals(Object o) {
    	boolean rtn = false;
    	
    	if (o instanceof Reference) {
    		rtn = ((Reference)o).getId().equals(this.getId());
    	}
    	
    	return rtn;
    }
}