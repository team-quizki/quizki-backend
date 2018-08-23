package com.haxwell.apps.quizki.entities;



import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.CascadeType;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Email;




import com.haxwell.apps.quizki.entities.UserRole;


//import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
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
    
    public UserRole getRole() {
    	return this.role;
    }

    public void setRole(UserRole role) {
    	this.role = role;
    }

    public void setEmail(String email) {
    	this.email = email;
    }
    
    public String getEmail() {
    	return this.email;
    }
    
    public void setDemographic(String demographic) {
    	this.demographic = demographic;
    }
    
    public String getDemographic() {
    	return this.demographic;
    }
    
    public void setFullname(String fullname) {
    	this.fullname = fullname;
    }
    
    public String getFullname() {
    	return this.fullname;
    }
    
    //does this method need all the new fields if Spring is using Jackson for JSON??
    public String toString() {
    	return id + " " + name + " " + password + " " + enabled + " " + fullname + " " + email + " " + demographic;
    }

	//uni-directional many-to-one association to UserRole
    @ManyToOne
    @JoinColumn(name = "user_role_id")
	private UserRole role;
    
    //this annotation would prevent the repository from saving the POSTED User to the DB
    //@JsonIgnore
    @NotBlank(message = "Password must be minimum 8 characters")
    public String password;
    
    @NotBlank
    public String name;
    public int enabled;
    
    @Email
    @NotBlank(message = "Email can't be blank")
    public String email;
    
    @NotBlank(message = "Name can't be blank")
    public String fullname;
    public String demographic;
    
    
    
    public User(UserRole role, String name, String password, String fullname, String email, String demographic) {
        this.name = name;
        this.password = password;
        this.enabled = 1;
        this.fullname = fullname;
        this.email = email;
        this.demographic = demographic;
        this.role = role;
        
    }

    //used exclusively by JPA
    protected User() { 
    	
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