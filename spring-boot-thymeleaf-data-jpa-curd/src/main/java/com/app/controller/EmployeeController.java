package com.app.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.app.entity.Employee;
import com.app.repo.EmployeeRepository;
import com.app.validators.EmployeeValidator;

@Controller
public class EmployeeController {

	@Autowired
	EmployeeRepository employeeRepository;

	@GetMapping(value = { "/", "/{id}" })
	public String viewHome(Model model, @PathVariable(value = "id", required = false) Integer id) {
		Employee employee = new Employee();
		if (id != null)
			employee = employeeRepository.findById(id).get();
		model.addAttribute("employeeForm", employee);
		return "index";
	}

	@PostMapping(value = "save")
	public String saveOrUpdate(Model model, @ModelAttribute("employeeForm") @Validated Employee employee,
			BindingResult bindingResult, RedirectAttributes attributes) {

		if (bindingResult.hasErrors()) {
			model.addAttribute("employeeForm", employee);
			return "index";
		}
		employee = employeeRepository.save(employee);
		if (employee != null)
			attributes.addFlashAttribute("success", "Employee saved with Id : " + employee.getId());
		else
			attributes.addFlashAttribute("error", "Something went wrong..");
		return "redirect:/";
	}

	@ModelAttribute("list")
	public List<Employee> listOfEmployee() {
		return employeeRepository.findAll();
	}

	// RequestParam : /delete?id= urlrewritting
	// PathVariable : /delete/3 //address based request

	@GetMapping(value = "delete/{id}")
	public String deleteEmployee(@PathVariable("id") Integer id, RedirectAttributes attributes) {
		employeeRepository.deleteById(id);
		attributes.addFlashAttribute("success", "Employee Deleted with Id : " + id);
		return "redirect:/";
	}

	@InitBinder("employeeForm")
	public void formValidator(WebDataBinder dataBinder) {
		dataBinder.setValidator(new EmployeeValidator());
	}

}
