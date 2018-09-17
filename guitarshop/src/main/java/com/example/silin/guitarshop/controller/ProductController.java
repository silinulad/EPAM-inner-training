package com.example.silin.guitarshop.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.example.silin.guitarshop.services.interfaces.ProductService;

@Controller
public class ProductController {

	@Autowired
	private ProductService productService;

	@GetMapping("/")
	public String getAllProducts(Model model) {
		model.addAttribute("products", productService.getAll());
		return "products";
	}

	@GetMapping("/login")
	public String login() {
		return "login";
	}

	@GetMapping("product/{id}")
	public String read(@PathVariable("id") long id, Model model) {
		model.addAttribute("product", productService.read(id));
		return "product";
	}

}
