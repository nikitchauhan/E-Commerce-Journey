package com.onlineSeller.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.onlineSeller.entity.User;
import com.onlineSeller.entity.UserPKId;






@Repository
public interface UserRepository extends JpaRepository<User ,UserPKId> {
	
	
	@Query(value="select is_active from user_details where username=?1", nativeQuery =true)
	public String getIsActive(String username);
	
	

}
