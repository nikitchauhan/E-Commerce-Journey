package com.onlineSeller.controller;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.onlineSeller.Service.UserService;
import com.onlineSeller.entity.User;




@RestController

public class userController {


	@GetMapping("/status")
	public String Appstatus() throws IOException {

		return "Working";
	}

	@GetMapping("/status2")
	public String Appstatus2() throws IOException {

		return "Working2";
	}
	@PostMapping("/status3")
	public String Appstatus3() throws IOException {

		return "Working3";
	}

@Autowired
UserService userService;

//
//static Bandwidth limit = Bandwidth.classic(1, Refill.intervally(1, Duration.of(2, null)));
//
//private static Bucket bucket = Bucket.builder().addLimit(limit).build();
//
//
//static long availableToken = bucket.getAvailableTokens();

@PostMapping("/saveUser")
public ResponseEntity<?> saveUser(@RequestBody User userDetails) throws IOException {

	return userService.saveUser(userDetails);
}
//
@PostMapping("/doLogin")
public ResponseEntity<?> doLogin(@RequestBody User userDetails, @RequestParam Integer seller_type,
		HttpServletRequest request) {
	//if (availableToken > 0 && bucket.tryConsume(1)) {
		return userService.doLogin(userDetails, seller_type, request);

//	} else {
//		return (ResponseEntity<?>) ResponseEntity.status(HttpStatus.TOO_MANY_REQUESTS);
//	}

	 

}
}