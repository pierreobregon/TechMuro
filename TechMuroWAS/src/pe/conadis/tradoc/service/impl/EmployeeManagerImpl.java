package pe.conadis.tradoc.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import pe.conadis.tradoc.dao.Dao;
import pe.conadis.tradoc.dao.EmployeeDAO;
import pe.conadis.tradoc.entity.EmployeeEntity;
import pe.conadis.tradoc.service.EmployeeManager;

@Service
public class EmployeeManagerImpl extends ServiceImpl<EmployeeEntity> implements EmployeeManager {

	@Autowired
    private EmployeeDAO employeeDAO;
	
	@Override
	protected Dao<EmployeeEntity> getDAO() {
		return employeeDAO;
	}
	

}
