package fr.opensagres.xdocreport.facade;

import fr.opensagres.xdocreport.model.EmployeeEntity;

public interface WebFacade {
	EmployeeEntity findById(Long id);
	void saveOrUpdateEmployee(EmployeeEntity employee);
}
