package com.devhow.service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.devhow.model.AppUser;
import com.devhow.model.UserRole;
import com.devhow.repo.AppUserRepo;

@Service("userDetailsService")
public class AppUserService implements UserDetailsService {

	@Autowired
	private AppUserRepo appUserRepo;

	@Transactional(readOnly = true)
	@Override
	public UserDetails loadUserByUsername(String username)
			throws UsernameNotFoundException {
		AppUser user = appUserRepo.findByUserName(username);
		List<GrantedAuthority> authorities = buildUserAuthority(user
				.getUserRole());

		return buildUserForAuthentication(user, authorities);
	}

	private UserDetails buildUserForAuthentication(AppUser user,
			List<GrantedAuthority> authorities) {
		return new User(user.getUsername(), user.getPassword(),
				user.isEnabled(), true, true, true, authorities);
	}

	private List<GrantedAuthority> buildUserAuthority(Set<UserRole> userRoles) {
		Set<GrantedAuthority> setAuths = new HashSet<GrantedAuthority>();
		for (UserRole userRole : userRoles) {
			setAuths.add(new SimpleGrantedAuthority(userRole.getRole()));
		}
		List<GrantedAuthority> Result = new ArrayList<GrantedAuthority>(setAuths);

		return Result;

	}
}
