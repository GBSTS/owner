package com.example.itmasys.entity;


public class Sysuser {
	private Integer userid;
	private String username;
	private String password;
	private Integer roleid;

	public void setUserid(Integer userid) {
		this.userid = userid;
	}

	public Integer getUserid() {
		return this.userid;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getUsername() {
		return this.username;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getPassword() {
		return this.password;
	}

	public void setRoleid(Integer roleid) {
		this.roleid = roleid;
	}

	public Integer getRoleid() {
		return this.roleid;
	}

	public Sysuser() {
	}

	public Sysuser(String username, String password, Integer roleid) {
		this.username = username;
		this.password = password;
		this.roleid = roleid;
	}

	public Sysuser(Integer userid, String username, String password, Integer roleid) {
		this.userid = userid;
		this.username = username;
		this.password = password;
		this.roleid = roleid;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) { return true; }
		if (o == null || getClass() != o.getClass()) {return false;}
		Sysuser that = (Sysuser) o;
		return userid.equals(that.userid);
	}

	@Override
	public int hashCode() {
		return java.util.Objects.hash(userid);
	}

	@Override
	public String toString() {
		return "Sysuser{" +
				"userid=" + userid +
				",username='" + username + "'" +
				",password='" + password + "'" +
				",roleid='" + roleid + "'" +
				'}';
	}

}