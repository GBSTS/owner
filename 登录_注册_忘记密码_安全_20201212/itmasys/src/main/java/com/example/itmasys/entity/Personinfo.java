package com.example.itmasys.entity;


public class Personinfo {
	private Integer userid;
	private String username;
	private String phone;
	private String email;
	private String address;
	private String education;
	private Integer cardid;

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
	
	public void setAddress(String address) {
		this.address = address;
	}
	
	public String getAddress() {
		return this.address;
	}
	
	public void setEducation(String education) {
		this.education = education;
	}
	
	public String getEducation() {
		return this.education;
	}
	
	public void setCardid(Integer cardid) {
		this.cardid = cardid;
	}
	
	public Integer getCardid() {
		return this.cardid;
	}

	public Personinfo() {
	}

	public Personinfo(String username, String phone, String email, String address, String education, Integer cardid) {
		this.username = username;
		this.phone = phone;
		this.email = email;
		this.address = address;
		this.education = education;
		this.cardid = cardid;
	}

	public Personinfo(Integer userid, String username, String phone, String email, String address, String education, Integer cardid) {
		this.userid = userid;
		this.username = username;
		this.phone = phone;
		this.email = email;
		this.address = address;
		this.education = education;
		this.cardid = cardid;
	}

	@Override
    public boolean equals(Object o) {
        if (this == o) { return true; }
        if (o == null || getClass() != o.getClass()) {return false;}
        Personinfo that = (Personinfo) o;
        return userid.equals(that.userid);
    }

    @Override
    public int hashCode() {
        return java.util.Objects.hash(userid);
    }
    
    @Override
    public String toString() {
        return "Personinfo{" +
				"userid=" + userid +
						",username='" + username + "'" + 
						",phone='" + phone + "'" + 
						",email='" + email + "'" + 
						",address='" + address + "'" + 
						",education='" + education + "'" + 
						",cardid='" + cardid + "'" + 
		                '}';
    }
	
}