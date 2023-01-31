package com.example2.persistance.entity;

import org.hibernate.annotations.DynamicUpdate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Lob;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Entity
@Data
@DynamicUpdate
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
@Table(name = "users")
public class Auth0 extends BaseEntity{
	
	private static final long serialVersionUID = 1L;
	
	@Lob
	@Column(name = "profile_img", length = 1000)
	private String profileImage;

	@Column(name = "first_name")
	private String firstName;

	@Column(name = "last_name")
	private String lastName;

	@Column(name = "email")
	private String email;

	@Column(name = "country_name")
	private String countryName;

	@Column(name = "mobile_no")
	private String mobileNo;

	@Column(name = "country_code")
	private String countryCode;

	@Column(name = "password")
	private String password;

	@Column(name = "provider")
	private String provider;

	@Column(name = "provider_id")
	private String providerId;

	@Column(columnDefinition = "tinyint(1) default 0", name = "is_blocked")
	private boolean blocked;

	
	

}
