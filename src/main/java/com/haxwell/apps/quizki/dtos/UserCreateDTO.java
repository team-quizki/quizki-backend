package com.haxwell.apps.quizki.dtos;


import javax.validation.constraints.Digits;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

public class UserCreateDTO {
	
	@NotBlank
	private String name;
	

	@Email
	@NotBlank
	private String email;
	
	@NotBlank
	private String password;
	
	@NotBlank
	private String fullname;
	

	@Digits(integer=6, fraction=0)
	private long roleId;
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getFullname() {
		return fullname;
	}
	public void setFullname(String fullname) {
		this.fullname = fullname;
	}
	public long getRoleId() {
		return roleId;
	}
	public void setRoleId(long roleId) {
		this.roleId = roleId;
	}
	
	public UserCreateDTO(String name, String email, String password, String fullname, long roleId) {
//		super();
		this.name = name;
		this.email = email;
		this.password = password;
		this.fullname = fullname;
		this.roleId = roleId;
	}
	

	public UserCreateDTO() {
		
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((email == null) ? 0 : email.hashCode());
		result = prime * result + ((fullname == null) ? 0 : fullname.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + ((password == null) ? 0 : password.hashCode());
		result = prime * result + (int) (roleId ^ (roleId >>> 32));
		return result;
	}
	
	@Override
	public String toString() {
		return "UserCreateDTO [name=" + name + ", email=" + email + ", password=" + password + ", fullname=" + fullname
				+ ", roleId=" + roleId + "]";
	}

}
