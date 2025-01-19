package com.onlineSeller.controller;

//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.request;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.onlineSeller.Service.ProfileService;
import com.onlineSeller.entity.SellerProfile;

@RestController
public class ProfileController {

	
	@Autowired
	ProfileService profileService;
	
	
	@PostMapping("/saveProfile")
    public ResponseEntity<?> saveProfile(@RequestBody SellerProfile sellerprofile, HttpServletRequest request) throws IOException {

		return profileService.saveUserProfile(sellerprofile, request);
	}
	
	
	
}
