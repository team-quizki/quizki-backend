package com.haxwell.apps.quizki.entities;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class UserRole {

    @Id
    @GeneratedValue
    private Long id;

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }
    
    public void setName(String name) { 
    	this.name = name;
    }
    
    public String toString() {
    	return id + " " + name;
    }

    public String name;

    public UserRole(String name) {
        this.name = name;
    }

    public UserRole() { 
    	
    }
}