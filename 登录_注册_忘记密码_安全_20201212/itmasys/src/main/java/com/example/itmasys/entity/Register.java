package com.example.itmasys.entity;


public class Register {
	private Integer registerid;
	private String username;
	private String password;
	private String phone;
	private String email;
	private Integer roleid;
	private Integer randomnumber;

	public Register() {
	}

	public Register(String username, String password, String phone, String email, Integer roleid, Integer randomnumber) {
		this.username = username;
		this.password = password;
		this.phone = phone;
		this.email = email;
		this.roleid = roleid;
		this.randomnumber = randomnumber;
	}

	public Register(Integer registerid, String username, String password, String phone, String email, Integer roleid, Integer randomnumber) {
		this.registerid = registerid;
		this.username = username;
		this.password = password;
		this.phone = phone;
		this.email = email;
		this.roleid = roleid;
		this.randomnumber = randomnumber;
	}

	public void setRegisterid(Integer registerid) {
		this.registerid = registerid;
	}
	
	public Integer getRegisterid() {
		return this.registerid;
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
	
	public void setPhone(String phone) {
		this.phone = phone;
	}
	
	public String getPhone() {
		return this.phone;
	}
	
	public void setEmail(String email) {
		this.email = email;
	}
	
	public String getEmail() {
		return this.email;
	}
	
	public void setRoleid(Integer roleid) {
		this.roleid = roleid;
	}
	
	public Integer getRoleid() {
		return this.roleid;
	}
	
	public void setRandomnumber(Integer randomnumber) {
		this.randomnumber = randomnumber;
	}
	
	public Integer getRandomnumber() {
		return this.randomnumber;
	}
	

	@Override
    public boolean equals(Object o) {
        if (this == o) { return true; }
        if (o == null || getClass() != o.getClass()) {return false;}
        Register that = (Register) o;
        return registerid.equals(that.registerid);
    }

    @Override
    public int hashCode() {
        return java.util.Objects.hash(registerid);
    }
    
    @Override
    public String toString() {
        return "Register{" +
				"registerid=" + registerid +
						",username='" + username + "'" + 
						",password='" + password + "'" + 
						",phone='" + phone + "'" + 
						",email='" + email + "'" + 
						",roleid='" + roleid + "'" + 
						",randomnumber='" + randomnumber + "'" + 
		                '}';
    }
	
}