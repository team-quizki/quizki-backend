package com.haxwell.apps.quizki.entities;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.CascadeType;

import com.haxwell.apps.quizki.entities.User;

@Entity
public class UserRole {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    public Long getId() {
        return id;
    }
    
    public void setId(long id) {
    	this.id = id;
    }

    public String getName() {
        return name;
    }
    
    public void setName(String name) { 
    	this.name = name;
    }
    
    public String toString() {
    	
    	String result = String.format("Role[id:%d , Name:%s]%n", this.id, this.name);
    	
    	return result;
    }

    public String name;

    public UserRole(String name) {
        this.name = name;
    }

    protected UserRole() { 
    	
    }
}