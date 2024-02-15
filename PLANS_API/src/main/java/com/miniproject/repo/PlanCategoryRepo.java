package com.miniproject.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.miniproject.entity.PlanCategory;

public interface PlanCategoryRepo extends JpaRepository<PlanCategory, Integer> {

}
