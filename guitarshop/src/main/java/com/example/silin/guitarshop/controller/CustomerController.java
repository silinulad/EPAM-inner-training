package com.example.silin.guitarshop.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.silin.guitarshop.model.Customer;
import com.example.silin.guitarshop.services.interfaces.CustomerService;
import com.example.silin.guitarshop.services.interfaces.ProductService;

@Controller
@RequestMapping("/profile")
public class CustomerController {

	private CustomerService customerService;
	private ProductService productService;

	@Autowired
	public CustomerController(CustomerService customerService, ProductService productService) {
		this.customerService = customerService;
		this.productService = productService;
	}

	@GetMapping("/list")
	public String getAllCustomers(Model model) {
		model.addAttribute("customers", customerService.getAll());
		model.addAttribute("products", productService.getAll());
		return "customers";
	}

	@GetMapping("/{id}")
	public String read(@PathVariable("id") long id, Model model) {
		model.addAttribute("customer", customerService.read(id));
		return "profile";
	}

	@GetMapping("/register")
	public String registerCustomerPage() {
		return "registration";
	}

	@PostMapping("/register")
	public String createCustomer(@ModelAttribute("customer") Customer customer) {
		customerService.create(customer);
		return "redirect:/all";
	}

	@GetMapping("/delete/{id}")
	public String deleteCustomer(@PathVariable("id") long id) {
		customerService.delete(id);
		return "redirect:/profile/list";
	}

	@GetMapping("/update/{id}")
	public String updateCustomerPage(@PathVariable("id") long id, Model model) {
		model.addAttribute("customer", customerService.read(id));
		return "updateCustomer";
	}

	@PostMapping("/update")
	public String updateCustomer(@ModelAttribute("customer") Customer customer) {
		customerService.update(customer);
		return "redirect:/profile/list" + customer.getId();
	}
}
