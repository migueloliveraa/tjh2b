package fr.opensagres.xdocreport.service;

import fr.opensagres.xdocreport.model.EmployeeEntity;

public interface EmployeeEntityService {
	EmployeeEntity findById(Long id);
	void saveOrUpdateEmployee(EmployeeEntity employee);
}
