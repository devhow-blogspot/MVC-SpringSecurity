package com.gfi.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

import com.gfi.model.AppUser;

public interface AppUserRepo extends JpaRepository<AppUser, String> {

	AppUser findByUserName(@Param("username") String username);
}
