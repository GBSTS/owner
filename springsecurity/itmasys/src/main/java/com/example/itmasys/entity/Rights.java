package com.example.itmasys.entity;


import lombok.Data;
import org.springframework.stereotype.Component;

@Data
public class Rights {
	private Integer rightid;
	private String url;
	private String rolenames;

	public String[] getRightsArray(){
		String[] authorities = rolenames.split(",");
		return authorities;
	}
}