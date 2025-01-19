package com.onlineSeller.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.onlineSeller.entity.SellerProfile;

public interface ProfileRepository extends JpaRepository<SellerProfile, Integer >{

}
