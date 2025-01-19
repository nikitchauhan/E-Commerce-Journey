package com.onlineSeller.entity;



import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.Table;

import lombok.Data;

@Data
@Entity
@IdClass(UserPKId.class)
@Table(name="user_details")
public class User {

	
	@Column(name="username")
	private String username;
	
	@Id
	@Column(name="loginid")
	private String loginid;
	
	@Id
	@Column(name="email")
	private String email;
	
	@Column(name="password")
	private String password;
	

	@Column(name="sellerkey")
    private String SellerKey;
	 
	@Column(name="islocked")
	private int islocked;
	
	@Column(name="is_active")
	private String isActive;
	
	@Column(name="privilege")
	private String  privilege;
	

	

	
	
	
	
	
}
