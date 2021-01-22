package com.example.swagger2.model;


import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@ApiModel(description = "权限类")
@Data
public class Rights {
	@ApiModelProperty(value = "监权编号")
	private Integer rightid;
	@ApiModelProperty(value = "访问url")
	private String url;
	@ApiModelProperty(value = "需要的权限角色")
	private String rolenames;

	public String[] getRightsArray(){
		String[] authorities = rolenames.split(",");
		return authorities;
	}
}