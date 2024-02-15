package com.miniproject.service;

import java.awt.Color;
import java.io.IOException;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;

import com.lowagie.text.Document;
import com.lowagie.text.DocumentException;
import com.lowagie.text.Font;
import com.lowagie.text.FontFactory;
import com.lowagie.text.PageSize;
import com.lowagie.text.Paragraph;
import com.lowagie.text.Phrase;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;
import com.miniproject.entity.EligibilityDetails;
import com.miniproject.repo.EligibilityDetailsRepo;
import com.miniproject.request.SearchRequest;
import com.miniproject.response.SearchResponse;

import jakarta.servlet.ServletOutputStream;
import jakarta.servlet.http.HttpServletResponse;

@Service
public class EligibilityServiceImpl implements EligibilityService {

	@Autowired
	private EligibilityDetailsRepo eligRepo;

	@Override
	public List<String> getUniquePlanNames() {

		return eligRepo.findPlanNames();
	}

	@Override
	public List<String> getUniquePlanStatus() {

		return eligRepo.findPlanStatuses();
	}

	@Override
	public List<SearchResponse> search(SearchRequest request) {

		/*
		 * List<SearchResponse> response = new ArrayList<>();
		 * 
		 * List<EligibilityDetails> entities = eligRepo.findAll();
		 * 
		 * for (EligibilityDetails entity : entities) {
		 * 
		 * SearchResponse sr = new SearchResponse();
		 * 
		 * BeanUtils.copyProperties(entity, sr);
		 * 
		 * response.add(sr);
		 * 
		 * }
		 */

		EligibilityDetails queryBuilder = new EligibilityDetails();

		String planName = request.getPlanName();
		if (planName != null && !planName.equals("")) {
			queryBuilder.setPlanName(planName);
		}

		
		String planStatus = request.getPlanStatus();
		if (planStatus != null && !planStatus.equals("")) {
			queryBuilder.setPlanStatus(planStatus);
		}

		LocalDate planStartDate = request.getPlanStartDate();
		if (planStartDate != null) {
			queryBuilder.setPlanStartDate(planStartDate);
		}
		
		
		LocalDate planEndDate = request.getPlanEndDate();
		if (planEndDate != null) {
			queryBuilder.setPlanEndDate(planEndDate);
		}
		
		
		Example<EligibilityDetails> example = Example.of(queryBuilder);
	
		return eligRepo.findAll(example).stream().map(entity -> {
			SearchResponse sr = new SearchResponse();
			BeanUtils.copyProperties(entity, sr);
			return sr;
		}).collect(Collectors.toList());

	}

	@Override
	public void generateExcel(HttpServletResponse response) throws Exception {
		
		List<EligibilityDetails> entities = eligRepo.findAll();
		
		HSSFWorkbook workBook = new HSSFWorkbook();
		HSSFSheet sheet = workBook.createSheet();
		HSSFRow createRow = sheet.createRow(0);
		createRow.createCell(0).setCellValue("Name");
		createRow.createCell(1).setCellValue("Email");
		createRow.createCell(2).setCellValue("Mobile");
		createRow.createCell(3).setCellValue("Gender");
		createRow.createCell(4).setCellValue("SSN");
		int i = 1;
		for(EligibilityDetails entity:entities) {
			HSSFRow dataRow = sheet.createRow(i);
			dataRow.createCell(0).setCellValue(entity.getName());
			dataRow.createCell(1).setCellValue(entity.getEmail());
			dataRow.createCell(2).setCellValue(String.valueOf(entity.getMobNumber()));
			dataRow.createCell(3).setCellValue(String.valueOf(entity.getGender()));
			dataRow.createCell(4).setCellValue(String.valueOf(entity.getSsn()));
			i++;
		}
		
		ServletOutputStream outputStream = response.getOutputStream();
		workBook.write(outputStream);
		workBook.close();
		outputStream.close();
	}

	@Override
	public void generatePdf(HttpServletResponse response) throws DocumentException, IOException {

		List<EligibilityDetails> entities = eligRepo.findAll();
		
		Document document = new Document(PageSize.A4);
		
		PdfWriter.getInstance(document, response.getOutputStream());
		
		document.open();
		Font font = FontFactory.getFont(FontFactory.HELVETICA_BOLD);
		font.setSize(18);
		font.setColor(Color.BLUE);
		
		Paragraph p = new Paragraph("Search Report", font);
		p.setAlignment(Paragraph.ALIGN_CENTER);
		
		document.add(p);
		
		PdfPTable table = new PdfPTable(5);
		table.setWidthPercentage(100f);
	    table.setWidths(new float[] {1.5f, 3.5f, 3.0f, 3.0f, 1.5f});
	    table.setSpacingBefore(10);
	    
	    
	    
	    
	    PdfPCell cell = new PdfPCell();
        cell.setBackgroundColor(Color.BLUE);
        cell.setPadding(5);
         
        font = FontFactory.getFont(FontFactory.HELVETICA);
        font.setColor(Color.WHITE);
         
        cell.setPhrase(new Phrase("Name", font));
        table.addCell(cell);
         
        cell.setPhrase(new Phrase("Email", font));
        table.addCell(cell);
         
        cell.setPhrase(new Phrase("Mob.No.", font));
        table.addCell(cell);
         
        cell.setPhrase(new Phrase("Gender", font));
        table.addCell(cell);
         
        cell.setPhrase(new Phrase("SSN", font));
        table.addCell(cell);
         
        
        for(EligibilityDetails entity:entities) {
        	table.addCell(entity.getName());
        	table.addCell(entity.getEmail());
        	table.addCell(String.valueOf(entity.getMobNumber()));
        	table.addCell(String.valueOf(entity.getGender()));
        	table.addCell(String.valueOf(entity.getSsn()));
        }
        
        document.add(table);
        
        document.close();
	    
	   
	         
	}

}
