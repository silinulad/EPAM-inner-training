package com.example.silin.guitarshop.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.silin.guitarshop.model.Customer;
import com.example.silin.guitarshop.model.Order;
import com.example.silin.guitarshop.model.Product;
import com.example.silin.guitarshop.services.interfaces.CustomerService;
import com.example.silin.guitarshop.services.interfaces.OrderService;
import com.example.silin.guitarshop.services.interfaces.StockService;

@Controller
@RequestMapping("/cart")
public class OrderController {

	@Autowired
	private OrderService orderService;

	@Autowired
	private CustomerService customerService;

	@Autowired
	private StockService stockService;

	@GetMapping("/{customerId}")
	public String getLastOrder(@PathVariable long customerId, Model model) {
		Order order = getOrder(customerId);
		model.addAttribute("lastOrder", order);
		model.addAttribute("orderProducts", orderService.getProductsForOrder(order.getId()));
		return "cart";
	}

	@GetMapping("addProduct/{customerId}/{productId}")
	public String addProdustToOrder(@PathVariable long customerId, @PathVariable long productId, Model model) {
		Order order = getOrder(customerId);
		stockService.readByProduct(productId);
		stockService.delete(productId);
		orderService.addProductToOrder(order.getId(), productId);
		return "redirect:/";
	}

	@GetMapping("/delete/{orderId}/{productId}")
	public String deletProductFromOrder(@PathVariable long orderId, @PathVariable long productId) {
		orderService.deleteProductFromOrder(orderId, productId);
		return "redirect:/cart/1";
	}

	@GetMapping("/update/{orderId}")
	public String updateTotalCost(@PathVariable long orderId) {
		List<Product> products = orderService.getProductsForOrder(orderId);
		double totalCost = 0.0;
		for (Product product : products) {
			totalCost += product.getPrice();
		}
		Order order = new Order();
		order.setId(orderId);
		order.setTotaValue(totalCost);
		orderService.update(order);
		return "redirect:/cart/1";
	}

	@GetMapping("/cancel/{orderId}")
	public String canceOrder(@PathVariable long orderId) {
		orderService.delete(orderId);
		return "redirect:/cart/1";
	}

	@GetMapping("/complete/{orderId}")
	public String completeOrder(@PathVariable long orderId) {
		orderService.completeOrder(orderId);
		return "redirect:/";
	}

	private Order getOrder(long customerId) {
		Order order = orderService.getLastCustomerOrder(customerId);
		if (order == null) {
			order = new Order();
			Customer customer = customerService.read(customerId);
			order.setCustomer(customer);
			orderService.create(order);
			order = orderService.getLastCustomerOrder(customerId);
		}
		return order;
	}

}
