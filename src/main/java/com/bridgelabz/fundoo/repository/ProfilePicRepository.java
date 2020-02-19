package com.bridgelabz.fundoo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.bridgelabz.fundoo.Entity.Profile;


public interface ProfilePicRepository extends JpaRepository<Profile, Long>{

	@Query(value =  " select * from profile where user_id = ?" , nativeQuery =  true)
	Profile findByUserId(Long userId);
	
}