package fr.opensagres.xdocreport.facade;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import fr.opensagres.xdocreport.model.EmployeeEntity;
import fr.opensagres.xdocreport.service.EmployeeEntityService;

@Service
public class WebFacadeImpl implements WebFacade {
	
	@Autowired
	private EmployeeEntityService employeeEntityService;
	
	@Override
	public EmployeeEntity findById(Long id) {
		return employeeEntityService.findById(id);
	}

	@Override
	public void saveOrUpdateEmployee(EmployeeEntity employee) {
		employeeEntityService.saveOrUpdateEmployee(employee);
	}

}
