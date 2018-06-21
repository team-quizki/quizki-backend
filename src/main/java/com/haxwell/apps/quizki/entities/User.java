package com.haxwell.apps.quizki.entities;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
public class User {

    @Id
    @GeneratedValue
    private Long id;

    public Long getId() {
        return id;
    }
    
    public void setId(Long id) {
    	this.id = id;
    }

    public String getPassword() {
        return password;
    }
    
    public void setPassword(String pass) {
    	this.password = pass;
    }

    public String getName() {
        return name;
    }
    
    public void setName(String name) { 
    	this.name = name;
    }
    
    public int getEnabled() {
    	return enabled;
    }
    
    public void setEnabled(int i) {
    	this.enabled = i;
    }
    
    public Set<UserRole> getRoles() {
    	if (this.roles == null) {
    		this.roles = new HashSet<UserRole>();
    	}
    	return this.roles;
    }

    public void setRoles(Set<UserRole> set) {
    	this.roles = set;
    }

    public String toString() {
    	return id + " " + name + " " + password + " " + enabled;
    }

	//uni-directional many-to-many association to UserRole
    @ManyToMany
	@JoinTable(
		name="userUserRoleMap"
		, joinColumns={
			@JoinColumn(name="userId")
			}
		, inverseJoinColumns={
			@JoinColumn(name="userRoleId")
			}
		)
	private Set<UserRole> roles;

    @JsonIgnore
    public String password;
    public String name;
    public int enabled;

    public User(Set<UserRole> roles, String name, String password) {
        this.name = name;
        this.password = password;
        this.enabled = 1;

        setRoles(roles);
    }

    public User() { 
    	
    }
    
    @Override
    public int hashCode() {
        return id == null ? 0 : id.hashCode();
    }    
    
    @Override
    public boolean equals(Object o) {
    	boolean rtn = false;
    	
    	if (o instanceof User) {
    		rtn = ((User)o).getId().equals(this.getId());
    	}
    	
    	return rtn;
    }
}