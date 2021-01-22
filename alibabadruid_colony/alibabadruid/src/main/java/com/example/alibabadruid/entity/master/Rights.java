package com.example.alibabadruid.entity.master;


public class Rights {
	private Integer rightid;
	private String url;
	private String rolenames;

	public void setRightid(Integer rightid) {
		this.rightid = rightid;
	}
	
	public Integer getRightid() {
		return this.rightid;
	}
	
	public void setUrl(String url) {
		this.url = url;
	}
	
	public String getUrl() {
		return this.url;
	}
	
	public void setRolenames(String rolenames) {
		this.rolenames = rolenames;
	}
	
	public String getRolenames() {
		return this.rolenames;
	}

	public Rights() {
	}

	public Rights(Integer rightid, String url, String rolenames) {
		this.rightid = rightid;
		this.url = url;
		this.rolenames = rolenames;
	}

	@Override
    public boolean equals(Object o) {
        if (this == o) { return true; }
        if (o == null || getClass() != o.getClass()) {return false;}
        Rights that = (Rights) o;
        return rightid.equals(that.rightid);
    }

    @Override
    public int hashCode() {
        return java.util.Objects.hash(rightid);
    }
    
    @Override
    public String toString() {
        return "Rights{" +
				"rightid=" + rightid +
						",url='" + url + "'" + 
						",rolenames='" + rolenames + "'" + 
		                '}';
    }
	
}