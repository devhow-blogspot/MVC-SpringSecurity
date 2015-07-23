package com.devhow.model;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "appusers")
@NamedQuery(name="AppUser.findByUserName", query="SELECT u from AppUser u where LOWER(u.username) like LOWER(:username)")
public class AppUser {
	@Id
	@Column(length = 45, nullable = false, unique = true)
	private String username;
	@Column(nullable = false, length = 60)
	private String password;
	@Column(name = "enabled", nullable = false)
	private boolean enabled;
	@OneToMany(fetch=FetchType.LAZY, mappedBy="appUser")
	private Set<UserRole> userRole = new HashSet<UserRole>(0);

	/**
	 * @return the username
	 */
	public String getUsername() {
		return username;
	}

	/**
	 * @param username
	 *            the username to set
	 */
	public void setUsername(String username) {
		this.username = username;
	}

	/**
	 * @return the password
	 */
	public String getPassword() {
		return password;
	}

	/**
	 * @param password
	 *            the password to set
	 */
	public void setPassword(String password) {
		this.password = password;
	}

	/**
	 * @return the enabled
	 */
	public boolean isEnabled() {
		return enabled;
	}

	/**
	 * @param enabled
	 *            the enabled to set
	 */
	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}

	/**
	 * @return the userRole
	 */
	public Set<UserRole> getUserRole() {
		return userRole;
	}

	/**
	 * @param userRole
	 *            the userRole to set
	 */
	public void setUserRole(Set<UserRole> userRole) {
		this.userRole = userRole;
	}

}
