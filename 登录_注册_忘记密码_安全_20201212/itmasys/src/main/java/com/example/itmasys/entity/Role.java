package com.example.itmasys.entity;


public class Role {
	private Integer roleid;
	private String rolename;

	public void setRoleid(Integer roleid) {
		this.roleid = roleid;
	}
	
	public Integer getRoleid() {
		return this.roleid;
	}
	
	public void setRolename(String rolename) {
		this.rolename = rolename;
	}
	
	public String getRolename() {
		return this.rolename;
	}

	public Role() {
	}

	public Role(String rolename) {
		this.rolename = rolename;
	}

	public Role(Integer roleid, String rolename) {
		this.roleid = roleid;
		this.rolename = rolename;
	}

	@Override
    public boolean equals(Object o) {
        if (this == o) { return true; }
        if (o == null || getClass() != o.getClass()) {return false;}
        Role that = (Role) o;
        return roleid.equals(that.roleid);
    }

    @Override
    public int hashCode() {
        return java.util.Objects.hash(roleid);
    }
    
    @Override
    public String toString() {
        return "Role{" +
				"roleid=" + roleid +
						",rolename='" + rolename + "'" + 
		                '}';
    }
	
}