package com.example2.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example2.business.constant.SucessMessage;
import com.example2.business.service.Auth0Service;
import com.example2.business.util.ResponseMaker;
import com.example2.business.util.SucessResponse;
import com.example2.web.model.AuthLoginResponse;
import com.example2.web.model.LoginModel;
import com.example2.web.model.SocialAuthLoginResponse;
import com.example2.web.model.SocialLoginModel;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/login")
public class Auth0Controller {
	
	@Autowired
	private Environment env;

	@Autowired
	private ResponseMaker responseMaker;

	@Autowired
	private Auth0Service auth0Service;
	
	@PostMapping
	public ResponseEntity<SucessResponse<AuthLoginResponse>> loginUser(@RequestBody @Valid LoginModel loginModel) {
		AuthLoginResponse loginResponse = auth0Service.userLogin(loginModel);

		return responseMaker.successResponse(env.getProperty(SucessMessage.SUCCESS), loginResponse);
	}

	@PostMapping(value = "/social")
	public ResponseEntity<SucessResponse<SocialAuthLoginResponse>> loginUserSocial(
			@RequestBody @Valid SocialLoginModel loginModel) {
		SocialAuthLoginResponse loginResponse = auth0Service.userLoginSocial(loginModel);

		return responseMaker.successResponse(env.getProperty(SucessMessage.SUCCESS), loginResponse);
	}

}
