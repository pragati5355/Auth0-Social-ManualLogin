package com.example2.web.model;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LoginModel {
	
	@Valid
	@Email
	private String username;
	
	@NotBlank
	private String password;

}
