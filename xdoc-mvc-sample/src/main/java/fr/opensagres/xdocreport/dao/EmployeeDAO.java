package fr.opensagres.xdocreport.dao;

import org.springframework.security.core.userdetails.UserDetails;

import fr.opensagres.xdocreport.model.EmployeeEntity;

public interface EmployeeDAO {
	public EmployeeEntity findById(Long id);
	public UserDetails loadUserByUsername(String username);
}
