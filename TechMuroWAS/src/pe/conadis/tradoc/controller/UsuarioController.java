package pe.conadis.tradoc.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import pe.conadis.tradoc.entity.EmployeeEntity;
import pe.conadis.tradoc.service.EmployeeManager;
import pe.conadis.tradoc.service.UsuarioManager;

@Controller
public class UsuarioController {
	
	//private static final Logger logger = Logger.getLogger(UsuarioController.class);

	@Autowired
	private UsuarioManager usuarioManager;

	@RequestMapping(value = "/usuario/add", method = RequestMethod.POST)
	public String addEmployee(
			@ModelAttribute(value = "employee") EmployeeEntity employee,
			BindingResult result) {
		// employeeManager.add(employee);
		return "redirect:/";
	}

	@RequestMapping("/usuario/delete/{employeeId}")
	public String deleteEmplyee(@PathVariable("employeeId") Integer employeeId) {
		// employeeManager.delete(employeeId);
		return "redirect:/";
	}

	public void setEmployeeManager(EmployeeManager employeeManager) {
		// this.employeeManager = employeeManager;
	}
}
