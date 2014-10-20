package fr.opensagres.xdocreport.service;

import fr.opensagres.xdocreport.dao.EmployeeDAO;
import fr.opensagres.xdocreport.model.EmployeeEntity;

public class EmployeeEntityServiceImpl implements EmployeeEntityService {

	private EmployeeDAO employeeDAO;
	
	@Override
	public EmployeeEntity findById(Long id) {
		return employeeDAO.findById(id);
	}

	public void setEmployeeDAO(EmployeeDAO employeeDAO) {
		this.employeeDAO = employeeDAO;
	}

	@Override
	public void saveOrUpdateEmployee(EmployeeEntity employee) {
		employeeDAO.saveOrUpdateEmployee(employee);
	}
	
	
}
