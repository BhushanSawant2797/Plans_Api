package com.miniproject.response;

import lombok.Data;

@Data
public class SearchResponse {
	
	private Integer eligId; 
	private String name;
	private Long mobNumber;
	private String email;
	private Character gender;
	private Long ssn;
	
	

}
