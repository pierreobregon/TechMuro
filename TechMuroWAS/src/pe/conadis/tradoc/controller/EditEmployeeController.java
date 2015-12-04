package pe.conadis.tradoc.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import pe.conadis.tradoc.entity.EmployeeEntity;
import pe.conadis.tradoc.service.EmployeeManager;


@Controller
public class EditEmployeeController {
	
	@Autowired
	private EmployeeManager employeeManager;
	
	@RequestMapping(value = "/emp.htm", method = RequestMethod.GET)
	public String listEmployees(ModelMap map) throws Exception 
	{
		map.addAttribute("employee", new EmployeeEntity());
		map.addAttribute("employeeList", employeeManager.getAll());
		
		return "afiches/afiches";
	}

	@RequestMapping(value = "/add.htm", method = RequestMethod.POST)
	public String addEmployee(@ModelAttribute(value="employee") EmployeeEntity employee, BindingResult result) throws Exception 
	{
		employeeManager.add(employee);
		return "redirect:/";
	}

	@RequestMapping("/delete/{employeeId}.htm")
	public String deleteEmplyee(@PathVariable("employeeId") Integer employeeId) throws Exception
	{
		employeeManager.delete(employeeId);
		return "redirect:/";
	}

	public void setEmployeeManager(EmployeeManager employeeManager) {
		this.employeeManager = employeeManager;
	}
}
