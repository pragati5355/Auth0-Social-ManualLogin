package com.example2.business.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.example2.business.constant.ExceptionConstant;
import com.example2.business.endpoint.AuthEndPoint;
import com.example2.business.exception.CustomException;
import com.example2.web.model.AuthLoginResponse;
import com.example2.web.model.LoginModel;
import com.example2.web.model.SocialAuthLoginResponse;
import com.example2.web.model.SocialLoginModel;

@Service
public class Auth0ServiceImpl implements Auth0Service{
	
	@Autowired
	private AuthEndPoint authEndPoint;


	@Override
	public AuthLoginResponse userLogin(LoginModel loginModel) {
		AuthLoginResponse loginResponse = null;
		try {
			
			loginResponse = authEndPoint.loginUser(loginModel.getUsername(), loginModel.getPassword());
		
		} catch (Exception ex) {
			throw new CustomException(ExceptionConstant.USER_NOT_FOUND, HttpStatus.NOT_FOUND.value());
		}
		return loginResponse;
	}

	@Override
	public SocialAuthLoginResponse userLoginSocial(SocialLoginModel loginModel) {
		
		SocialAuthLoginResponse loginResponse = null;
		
		try {
			
			loginResponse = authEndPoint.loginUserSocial(loginModel.getCode());
		
		} catch (Exception ex) {
			throw new CustomException(ExceptionConstant.BAD_REQUEST, HttpStatus.BAD_REQUEST.value());
		}

		return loginResponse;
	}

}
