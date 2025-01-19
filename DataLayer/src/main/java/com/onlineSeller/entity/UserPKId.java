

package com.onlineSeller.entity;

import java.io.Serializable;

import javax.persistence.Column;

import lombok.EqualsAndHashCode;

@SuppressWarnings("serial")
@EqualsAndHashCode
public class UserPKId implements Serializable
{

	
	@Column(name="loginid")
	private String loginid;
	
	


	@Column(name="email")
	private String email;


	


	public String getEmail() {
		return email;
	}


}
