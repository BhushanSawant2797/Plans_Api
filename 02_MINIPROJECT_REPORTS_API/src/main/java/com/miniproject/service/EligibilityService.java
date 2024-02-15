package com.miniproject.service;

import java.io.IOException;
import java.util.List;

import com.lowagie.text.DocumentException;
import com.miniproject.request.SearchRequest;
import com.miniproject.response.SearchResponse;

import jakarta.servlet.http.HttpServletResponse;

public interface EligibilityService { 
	
	public List<String> getUniquePlanNames();
	
	public List<String> getUniquePlanStatus();      
	
	public List<SearchResponse> search(SearchRequest request);
	
	public void generateExcel (HttpServletResponse response) throws Exception;
	//public HttpServletResponse generatedExcel(); this is also valid  
	
	public void generatePdf (HttpServletResponse response) throws DocumentException, IOException;
	
	
	
	
	

}  
