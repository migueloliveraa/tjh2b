package fr.opensagres.xdocreport.dao;

import org.springframework.security.core.userdetails.UserDetails;

public interface EmployeeDAO {
	public UserDetails loadUserByUsername(String username);
}
