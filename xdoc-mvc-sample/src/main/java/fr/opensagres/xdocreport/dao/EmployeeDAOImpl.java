package fr.opensagres.xdocreport.dao;

import java.util.ArrayList;
import java.util.List;

import org.springframework.dao.DataAccessException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import fr.opensagres.xdocreport.dao.common.BaseDAOHibernate;
import fr.opensagres.xdocreport.model.EmployeeEntity;

public class EmployeeDAOImpl extends BaseDAOHibernate implements EmployeeDAO,UserDetailsService  {

	@Override
	public UserDetails loadUserByUsername(String username)  throws UsernameNotFoundException, DataAccessException {
		System.out.println("Getting access details from employee dao !!");
        
        // Ideally it should be fetched from database and populated instance of
        // #org.springframework.security.core.userdetails.User should be returned from this method
		
		List<GrantedAuthority> grantedAuthorities= new ArrayList<GrantedAuthority>();
		grantedAuthorities.add(new SimpleGrantedAuthority("ROLE_USER") );
//        UserDetails user = new User(username, "password", true, true, true, true, new GrantedAuthority[]{ new GrantedAuthorityImpl("ROLE_USER") });
		UserDetails user = new User(username, "123456", true, true, true, true, grantedAuthorities);
        return user;
	}

	@Override
	public EmployeeEntity findById(Long id) {
		return findById(EmployeeEntity.class, id);
	}

	@Override
	public void saveOrUpdateEmployee(EmployeeEntity employee) {
		save(employee);
	}

}
