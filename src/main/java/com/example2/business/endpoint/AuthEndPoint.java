package com.example2.business.endpoint;

import java.nio.charset.StandardCharsets;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import com.example2.business.constant.AuthConstant;
import com.example2.business.constant.ExceptionConstant;
import com.example2.business.exception.CustomException;
import com.example2.web.model.AuthLoginResponse;
import com.example2.web.model.LoginTokenRequest;
import com.example2.web.model.SocialAuthLoginResponse;
import com.example2.web.model.SocialLoginTokenRequest;
import com.fasterxml.jackson.databind.ObjectMapper;

@Component	
public class AuthEndPoint {

	
	public AuthLoginResponse loginUser(String username, String password) {
		
		LoginTokenRequest accessTokenRequest = new LoginTokenRequest();
		AuthLoginResponse userResponse = null;

		accessTokenRequest.setClient_id(AuthConstant.AUTH0_ID);
		accessTokenRequest.setGrant_type(AuthConstant.PASSWORD_LOGIN_GRANT_TYPE);
		accessTokenRequest.setAudience(AuthConstant.AUTH0_AUDIENCE);
		accessTokenRequest.setScope(AuthConstant.AUTH0_SCOPE);
		accessTokenRequest.setUsername(username);
		accessTokenRequest.setPassword(password);
		accessTokenRequest.setClient_secret(AuthConstant.AUTH0_SECRET);
		
		try (CloseableHttpClient httpClient = HttpClientBuilder.create().build()) {
			
			HttpPost postReq = new HttpPost(AuthConstant.AUTH0_OAUTH_TOKEN_URL);

			ObjectMapper mapper = new ObjectMapper();

			postReq.setEntity(new StringEntity(mapper.writeValueAsString(accessTokenRequest)));
			postReq.setHeader(AuthConstant.ACCEPT, ContentType.APPLICATION_JSON.toString());
			postReq.setHeader(AuthConstant.CONTENT_TYPE, ContentType.APPLICATION_JSON.toString());
			HttpResponse response = (HttpResponse) httpClient.execute(postReq);
			
			
			if (response.getStatusLine().getStatusCode() == HttpStatus.OK.value()) {
				userResponse = new ObjectMapper().readValue(
						EntityUtils.toString(response.getEntity(), StandardCharsets.UTF_8), AuthLoginResponse.class);
			} else if (response.getStatusLine().getStatusCode() == HttpStatus.FORBIDDEN.value()) {
				throw new CustomException(ExceptionConstant.WRONG_EMAIL_OR_PSWD,
						HttpStatus.FORBIDDEN.value());
			} else if (response.getStatusLine().getStatusCode() == HttpStatus.UNAUTHORIZED.value()) {
				throw new CustomException(ExceptionConstant.BAD_REQUEST,
						HttpStatus.UNAUTHORIZED.value());
			} else {
				throw new CustomException(ExceptionConstant.INVALID_REQUEST,
						HttpStatus.BAD_REQUEST.value());
			}


		} catch (Exception ex) {	
			throw new CustomException(ExceptionConstant.INTERNAL_SERVER_ERROR,
					HttpStatus.INTERNAL_SERVER_ERROR.value());
		}
		
		
		return userResponse;
		
	}
	
	public SocialAuthLoginResponse loginUserSocial(String code) {
		
		System.out.println("code--> " + code);
		SocialAuthLoginResponse res = new SocialAuthLoginResponse();

		SocialLoginTokenRequest socialLoginTokenRequest = new SocialLoginTokenRequest();

		socialLoginTokenRequest.setClient_id(AuthConstant.AUTH0_ID);
		System.out.println("Auth0 :" + AuthConstant.AUTH0_ID);
		socialLoginTokenRequest.setClient_secret(AuthConstant.AUTH0_SECRET);
		socialLoginTokenRequest.setGrant_type(AuthConstant.ACCESS_TOKEN_GRANT_TYPE);
		socialLoginTokenRequest.setRedirect_uri(AuthConstant.AUTH0_REDIRECT_URI);
		socialLoginTokenRequest.setCode(code);
		
		
		try (CloseableHttpClient httpClient = HttpClientBuilder.create().build()) {
			HttpPost postReq = new HttpPost(AuthConstant.AUTH0_OAUTH_TOKEN_URL);

			ObjectMapper mapper = new ObjectMapper();

			postReq.setEntity(new StringEntity(mapper.writeValueAsString(socialLoginTokenRequest)));
			postReq.setHeader(AuthConstant.ACCEPT, ContentType.APPLICATION_JSON.toString());
			postReq.setHeader(AuthConstant.CONTENT_TYPE, ContentType.APPLICATION_JSON.toString());
			HttpResponse response = httpClient.execute(postReq);
			if (response.getStatusLine().getStatusCode() == HttpStatus.OK.value()) {
				res = new ObjectMapper().readValue(EntityUtils.toString(response.getEntity(), StandardCharsets.UTF_8),
						SocialAuthLoginResponse.class);
			} else if (response.getStatusLine().getStatusCode() == HttpStatus.FORBIDDEN.value()) {
				throw new CustomException(ExceptionConstant.WRONG_CODE, HttpStatus.FORBIDDEN.value());
			} else if (response.getStatusLine().getStatusCode() == HttpStatus.UNAUTHORIZED.value()) {
				throw new CustomException(ExceptionConstant.BAD_REQUEST, HttpStatus.FORBIDDEN.value());
			}
		}
		catch (Exception ex) {
			throw new CustomException(ExceptionConstant.INTERNAL_SERVER_ERROR,
					HttpStatus.INTERNAL_SERVER_ERROR.value());
		}
		
		return res;
		
		
	}

}
