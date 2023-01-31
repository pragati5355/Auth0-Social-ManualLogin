package com.example2.persistance.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example2.persistance.entity.Auth0;

@Repository
public interface Auth0Repository extends JpaRepository<Auth0, Integer>{


	
}
