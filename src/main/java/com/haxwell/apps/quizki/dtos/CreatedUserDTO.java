package com.haxwell.apps.quizki.dtos;



public class CreatedUserDTO {

	private long id;
	private String name;
	private String email;
	private String fullname;
	private long roleId;
	
	public long getId() {
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
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
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
	
	public CreatedUserDTO(long id, String name, String email, String fullname, long roleId) {
		super();
		this.id = id;
		this.name = name;
		this.email = email;
		this.fullname = fullname;
		this.roleId = roleId;
	}
	
	public CreatedUserDTO() {
		// TODO Auto-generated constructor stub
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((email == null) ? 0 : email.hashCode());
		result = prime * result + ((fullname == null) ? 0 : fullname.hashCode());
		result = prime * result + (int) (id ^ (id >>> 32));
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + (int) (roleId ^ (roleId >>> 32));
		return result;
	}
	
	@Override
	public String toString() {
		return "CreatedUserDTO [id=" + id + ", name=" + name + ", email=" + email + ", fullname=" + fullname
				+ ", roleId=" + roleId + "]";
	}
	
}
