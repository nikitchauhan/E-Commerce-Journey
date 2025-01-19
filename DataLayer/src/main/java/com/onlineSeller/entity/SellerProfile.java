package com.onlineSeller.entity;



import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

//import javax.persistence.Column;
//import javax.persistence.Entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class SellerProfile {

	//Sell
	
	@Column(name="Seller_id")
	  private int seller_id;
	
	
	//Seller Name
	@Id
	
	@Column(name="Seller_name")
	private String SellerName;
	
	//Seller Address
	@Column(name="Office_Address")
	private  String officeAddress;


	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
