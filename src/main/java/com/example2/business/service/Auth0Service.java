package com.example2.business.service;

import com.example2.web.model.AuthLoginResponse;
import com.example2.web.model.LoginModel;
import com.example2.web.model.SocialAuthLoginResponse;
import com.example2.web.model.SocialLoginModel;

public interface Auth0Service {
	
	AuthLoginResponse userLogin(LoginModel loginModel);
	
	SocialAuthLoginResponse userLoginSocial(SocialLoginModel loginModel);

}
