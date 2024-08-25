package com.job.app.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.job.app.entity.JobEntity;

public interface JobRepository extends JpaRepository<JobEntity, Long> {
	
	@Query("SELECT j FROM JobEntity j WHERE " +
	           "(:keywords IS NULL OR j.title LIKE %:keywords% OR j.description LIKE %:keywords%) AND " +
	           "(:location IS NULL OR j.location = :location) AND " +
	           "(:requiredSkills IS NULL OR EXISTS (SELECT s FROM j.requiredSkills s WHERE s IN :requiredSkills))")
	    List<JobEntity> searchJobs(@Param("keywords") String keywords,
	                               @Param("location") String location,
	                               @Param("requiredSkills") List<String> requiredSkills);
	
	
}
