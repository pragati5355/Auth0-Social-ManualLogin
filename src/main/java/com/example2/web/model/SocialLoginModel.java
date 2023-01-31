package com.example2.web.model;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SocialLoginModel {
	
	@Valid
	@NotBlank
	private String code;

}
