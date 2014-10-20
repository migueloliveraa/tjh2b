package fr.opensagres.xdocreport.dao;

import org.springframework.security.core.userdetails.UserDetails;

import fr.opensagres.xdocreport.model.EmployeeEntity;

public interface EmployeeDAO {
	EmployeeEntity findById(Long id);
	void saveOrUpdateEmployee(EmployeeEntity employee);
	UserDetails loadUserByUsername(String username);
}
