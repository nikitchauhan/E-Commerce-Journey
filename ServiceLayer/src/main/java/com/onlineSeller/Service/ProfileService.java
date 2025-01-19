package com.onlineSeller.Service;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import com.onlineSeller.component.AuthenticationException;
import com.onlineSeller.component.ExceptionResponse;
import com.onlineSeller.component.JwtAuthentication;
import com.onlineSeller.entity.SellerProfile;
import com.onlineSeller.entity.User;
import com.onlineSeller.repository.ProfileRepository;

@Service
public class ProfileService {

	@Autowired
	ProfileRepository profileRepository;

	@Autowired(required=true)
	JwtAuthentication jwtAuth;

	public ResponseEntity<String> saveUserProfile(@RequestBody SellerProfile sellerprofileDetails,
			HttpServletRequest request) {

		ExceptionResponse exceptionResponse = null;
		ResponseEntity response = null;
	
		// jwt Auth

		if (jwtAuth.authenticate(request).equals("failed")) {
			
			throw new AuthenticationException("Authentication Error");

		}

		try {	
			
			int SellerExists = 0;

			List<SellerProfile> sellers = profileRepository.findAll();

			for (SellerProfile seller : sellers) {

				if (seller.getSellerName().equals(sellerprofileDetails.getSellerName())) {
					SellerExists = 1;
					System.out.println("Seller Name is already Present");
					exceptionResponse = new ExceptionResponse("Seller  not Registered", "Seller Name already Present ",
							"Success");
					return response = new ResponseEntity<Object>(exceptionResponse, HttpStatus.INTERNAL_SERVER_ERROR);

				}

			}


			if (SellerExists == 0) {

				SellerProfile profile = (SellerProfile) profileRepository.save(sellerprofileDetails);
				exceptionResponse = new ExceptionResponse("Seller Details Saved", "Seller Details Saved","Success");
				return response = new ResponseEntity<Object>(exceptionResponse, HttpStatus.OK);

			}

		} 		catch (Exception e) {

		}

		return response;

	}

}
