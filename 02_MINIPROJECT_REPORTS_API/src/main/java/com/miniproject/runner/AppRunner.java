package com.miniproject.runner;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import com.miniproject.entity.EligibilityDetails;
import com.miniproject.repo.EligibilityDetailsRepo;

@Component
public class AppRunner implements ApplicationRunner {

	@Autowired 
	private EligibilityDetailsRepo repo;
	
	@Override
	public void run(ApplicationArguments args) throws Exception {

		EligibilityDetails entity1 = new EligibilityDetails();
		entity1.setEligId(1);
		entity1.setName("John");
		entity1.setMobNumber(12756l);
		entity1.setGender('M');
		entity1.setSsn(23423l);
		entity1.setPlanName("SNAP");
		entity1.setPlanStatus("Approved");
		
		repo.save(entity1);
		
		
		EligibilityDetails entity2 = new EligibilityDetails();
		entity2.setEligId(2);
		entity2.setName("Smith");
		entity2.setMobNumber(12756l);
		entity2.setGender('M');
		entity2.setSsn(23433l);
		entity2.setPlanName("CCAP");
		entity2.setPlanStatus("Denied");
		
		repo.save(entity2);
		
		EligibilityDetails entity3 = new EligibilityDetails();
		entity3.setEligId(3);
		entity3.setName("Jason");
		entity3.setMobNumber(1275612367l);
		entity3.setGender('M');
		entity3.setSsn(2342343678l);
		entity3.setPlanName("Medicaid");
		entity3.setPlanStatus("Closed");
		
		repo.save(entity3);
		
	}

}
