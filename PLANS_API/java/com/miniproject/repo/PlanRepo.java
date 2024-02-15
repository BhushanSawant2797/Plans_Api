package com.miniproject.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.miniproject.entity.Plan;

public interface PlanRepo  extends JpaRepository<Plan, Integer>{

}
