package com.onlineSeller.Service;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.onlineSeller.component.ExceptionResponse;
import com.onlineSeller.component.JwtUtil;
import com.onlineSeller.component.Security;
import com.onlineSeller.component.UserException;
import com.onlineSeller.entity.User;
import com.onlineSeller.repository.UserRepository;

@Service
public class UserService {

	@Value("${seller_key}")
	public String seller_key;

	@Autowired
	UserRepository userRepository;

	@Autowired
	JwtUtil jwtUtil;

	public ResponseEntity<?> saveUser(User userDetails) throws IOException {
		String invalidDetails = "False";
		ExceptionResponse exceptionResponse = null;
		ResponseEntity<?> responseEntity = null;
		try {

			invalidDetails = validateEmailPassword(userDetails);
			if (!invalidDetails.equals("false")) {
				System.out.println("User not Registered Details are not Matching ");
				exceptionResponse = new ExceptionResponse("User not Registered");

				return responseEntity = new ResponseEntity<Object>(exceptionResponse, HttpStatus.INTERNAL_SERVER_ERROR);
			}

			// check if email id and login id Exists in the system

			if (invalidDetails.equals("false")) {
				int userExists = 0;
				List<User> users = userRepository.findAll();

				for (User user : users) {
					if (user.getEmail().equals(userDetails.getEmail())) {
						userExists = 1;
						exceptionResponse = new ExceptionResponse("User Not Registered Email is Already in Use!!",
								"Error in saving userDetails in User service Class", "Error");

						return responseEntity = new ResponseEntity<Object>(exceptionResponse,
								HttpStatus.INTERNAL_SERVER_ERROR);

					}

					if (user.getLoginid().equals(userDetails.getLoginid())) {
						userExists = 1;
						exceptionResponse = new ExceptionResponse("User Not Registered login  is Already in Use!!",
								"Error in saving userDetails in User service Class", "Error");

						return responseEntity = new ResponseEntity<Object>(exceptionResponse,
								HttpStatus.INTERNAL_SERVER_ERROR);

					}
				}

				if (userExists == 0) {

					String secretKey = "Amazon@123";
					// secretKey = new String(Base64.getDecoder().decode(seller_key));

					// userDetails.setPassword(userDetails.getPassword());

					User newUser = userRepository.save(userDetails);
					if (newUser != null) {
						exceptionResponse = new ExceptionResponse("User Registered Successfully!!", "Success", "");

						responseEntity = new ResponseEntity<Object>(exceptionResponse, HttpStatus.OK);
					} else {
						System.out.println("Error in Saving User Registration!");
						exceptionResponse = new ExceptionResponse("Error in Saing User Registration");

						responseEntity = new ResponseEntity<Object>(exceptionResponse,
								HttpStatus.INTERNAL_SERVER_ERROR);

					}

				}

			}

		} catch (Exception e) {
			e.printStackTrace();

			
		}
		return responseEntity;

	}

	// Login

	public ResponseEntity<?> doLogin(User userDetails, Integer seller_type, HttpServletRequest request) {

		boolean expired = false;
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
		String redirect = "", token = "", loginid = "", password = "", username = "", encryptRole = "";

		String secretKey = "Amazon@123";
		// new String(Base64.getDecoder().decode(seller_key));
		ExceptionResponse exceptionResponse = null;
		ResponseEntity<?> responseEntity = null;
		String end_date_string = "";

		String current_date_string = "";
		// LocalDataTime localDateTime=LocalDateTime.now();
		boolean isUserLocked = false, isUserLoginSuccessful = false;
		int attempts = 0;
//		String secretKeyPass = "amazon";
//
//		
//		Base64.Decoder decoder = Base64.getDecoder();
//		secretKeyPass = new String(decoder.decode(secretKeyPass));
//
//		try {
//			String ddtd = "fjeiopfji";// Setting.Repository.getDttd(),
//			end_date_string = Security.decrypt(ddtd, secretKeyPass);
//
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//		if (current_date_string.length() == 0) {
//			Date date = new Date(System.currentTimeMillis());
//			current_date_string = formatter.format(date);
//
//		}
//		try {
//
//			Date current_date = formatter.parse(current_date_string);
//			Date end_date = formatter.parse(end_date_string);
//
//			if (current_date.compareTo(end_date) > 0) {
//
//				expired = true;
//				System.out.println("Seller License Expired Please contact to Head Office");
//				exceptionResponse = new ExceptionResponse("Seller License Expired", "Error in do login User Service",
//						"Error");
//
//				return responseEntity = new ResponseEntity<Object>(exceptionResponse, HttpStatus.INTERNAL_SERVER_ERROR);
//
//			}
//
//		} catch (Exception e) {
//			e.printStackTrace();
//
//		}

		if (!expired) {

			try {

				if (seller_type == 1 || seller_type == 2 || seller_type == 3) {
					// loginid=new
					// String(Base64.getDecoder().decode(Security.decrypt(userDetails.getLoginId())));
					// password=new
					// String(Base64.getDecoder().decode(Security.decrypt(userDetails.getLoginId())));
					loginid = userDetails.getLoginid();
					password = userDetails.getPassword();

				} else {
					// loginid=Aes.aesDecrypt(userDetails.getLoginId());
					// password=Aes.aesdecrypt(userDetails.getPassword());
					loginid = userDetails.getLoginid();
					password = userDetails.getPassword();
				}
				List<User> users = userRepository.findAll();

				if (users.isEmpty() || users == null) {
					exceptionResponse = new ExceptionResponse("No Users Registered Please Register",
							"Error in User Service Class ", "Error");

					return responseEntity = new ResponseEntity<Object>(exceptionResponse,
							HttpStatus.INTERNAL_SERVER_ERROR);
				}

				for (User user : users) {
					if (loginid.equalsIgnoreCase(user.getLoginid())) {

						if (user.getIslocked() == 3) {
							isUserLocked = true;
							System.out.println("Seller Account Got Locked please Contact PlatForm");
							exceptionResponse = new ExceptionResponse("Account is Locked due to 3 Wrong Attempts");
							responseEntity = new ResponseEntity<Object>(exceptionResponse,
									HttpStatus.INTERNAL_SERVER_ERROR);

							redirect = "Lock";

							break;

						}

						else if (user.getIslocked() < 3) {
							if (loginid.equalsIgnoreCase(user.getLoginid())
									&& password.equalsIgnoreCase(user.getPassword())) {
								// encryptRole=Security.encrypt(user.getPrivilage(),secretkey);
								username = user.getUsername();

								isUserLoginSuccessful = true;
								break;
							}

							if (loginid.equalsIgnoreCase(user.getLoginid())
									&& !password.equalsIgnoreCase(user.getPassword())) {
								attempts = user.getIslocked();

								if (user.getIslocked() <= 2) {

									attempts++;
									user.setIslocked(attempts);

									userRepository.save(user);
									if (attempts < 3) {

										System.out.println("Invalid Input   !");

										exceptionResponse = new ExceptionResponse(
												"Invalid Input Attempts left" + (3 - attempts), "Error in ", "");
										responseEntity = new ResponseEntity<Object>(exceptionResponse,
												HttpStatus.INTERNAL_SERVER_ERROR);

										break;

									}

								}

								if (attempts == 3) {

									System.out.println("Account is Locked due to 3 attmpts");

									exceptionResponse = new ExceptionResponse("Account is Locked due to 3 attmpts",
											"Error in do login userService", "Error");
									responseEntity = new ResponseEntity<Object>(exceptionResponse,
											HttpStatus.INTERNAL_SERVER_ERROR);
									redirect = "lock";
									break;
								}

							}

						}
					} else {
						System.out.println("first Register the User");
					}
				}

				if (!isUserLocked) {
					if (isUserLoginSuccessful) {
						if (seller_type == 1)
							redirect = "Whole Seller";
						if (seller_type == 1)
							redirect = "Retailer Seller";

						String loginidAndRole = loginid + "," + encryptRole;
						token = jwtUtil.generateToken(loginidAndRole);

						// userRepository.setIsActive("true",username);

						System.out.println("user Logged In Successfully ");

						User user = null;

						for (User us : users) {
							if (loginid.equalsIgnoreCase(us.getLoginid())) {
								user = us;
							}

						}

						Map<String, Object> responseObject = new HashMap<String, Object>();

						responseObject.put("redirect", redirect);
						responseObject.put("seller_type", seller_type);
						responseObject.put("username", username);
						responseObject.put("token", token);
						// responseObject.put("role",user.getPrivilage());

						responseEntity = new ResponseEntity<Object>(responseObject, HttpStatus.OK);

					} else {

						if (attempts != 0) {

							System.out.println("Invalid Inputs  Please Try Again");

							exceptionResponse = new ExceptionResponse("Account is Locked due to 3 attmpts",
									"Error in do login userService", "Error");
							responseEntity = new ResponseEntity<Object>(exceptionResponse,
									HttpStatus.INTERNAL_SERVER_ERROR);

						} else {
							throw new UserException("User does not Exist");
						}
					}
				}

			} catch (Exception e) {
				throw new UserException(e.getMessage());

			}
		}
		return responseEntity;

	}

	// Refresh Token
	@SuppressWarnings("resource")
	public ResponseEntity<?> refreshToken(String userName, HttpServletRequest request) throws Exception {

		Map<String, Object> responseObject = new HashMap<String, Object>();

		ResponseEntity responseEntity = null;

		try {

			List<User> users = userRepository.findAll();

			for (User user : users) {

				if (userName.equalsIgnoreCase(user.getUsername())) {
					responseObject.put("refreshToken", jwtUtil.generateToken(userName));
				}
			}
			if (responseObject.isEmpty()) {
				System.out.println("username Doesnot Exist");
				// throw new AuthenticationException("Username does not Exist");
			}

		} catch (Exception e) {
			throw new Exception(e.getMessage());
		}

		return responseEntity;
	}

	public String validateEmailPassword(User userDetails) {
		boolean checkDetails = true;
		String seller_key = userDetails.getSellerKey();
		checkDetails = userDetails.getUsername().matches("^[A-Za-z][A-Za-z0-9_]{5,30}$");

		if (!checkDetails) {
			return "Username";
		}
		checkDetails = userDetails.getLoginid().matches("^[A-Za-z][A-Za-z0-9_]{5,30}$");
		if (!checkDetails) {
			return "Loginid";
		}
//		if(true) {
//			System.out.println("Checking email");
//		checkDetails = userDetails.getEmail()
//				.matches("^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,}$");
//
//		}

		if (!checkDetails) {

			return "EmailId";
		}

		// String checkDetailsPass = new
		// String(Base64.getDecoder().decode((userDetails.getPassword())));
//
//		checkDetails = checkDetailsPass
//				.matches("^(?=.*[a-z]{1,})(?=.*{A-Z]{1,})(?=.*[0-9]{1,})(?=.*[!@#\\$%\\^&\\*]).{8,}$");

//		if (userDetails.getUsername().contains(checkDetailsPass)) {
//			return "Username or Password is Incorrect";

		// }
		if (!checkDetails) {
			return "password";
		}
		checkDetails = userDetails.getSellerKey().equals(seller_key);

		if (!checkDetails) {
			System.out.println("Email is correct");
			return "seller_key";
		}

		return "false";
	}

}
