package com.haxwell.apps.quizki.entities;


import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;


@Entity
public class Reference {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

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