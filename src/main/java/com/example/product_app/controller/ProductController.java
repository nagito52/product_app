package com.example.product_app.controller;

import jakarta.validation.Valid;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.product_app.entity.Product;
import com.example.product_app.form.ProductForm;
import com.example.product_app.service.ProductService;

@Controller
public class ProductController {

	private final ProductService productService;

	public ProductController(ProductService productService) {
		this.productService = productService;
	}

	@GetMapping("/")
	public String index(Model model, Authentication authentication) {
		model.addAttribute("products", productService.findAll());
		model.addAttribute("productForm", new ProductForm());

		model.addAttribute("loginUserName", authentication.getName());
		model.addAttribute("isAdmin", hasAdminRole(authentication));

		return "index";
	}

	@PostMapping("/add")
	public String add(@Valid @ModelAttribute("productForm") ProductForm form,
			BindingResult bindingResult,
			Model model,
			Authentication authentication) {

		if (bindingResult.hasErrors()) {
			model.addAttribute("products", productService.findAll());
			model.addAttribute("loginUserName", authentication.getName());
			model.addAttribute("isAdmin", hasAdminRole(authentication));
			return "index";
		}

		Product product = new Product();
		product.setName(form.getName());
		product.setPrice(form.getPrice());
		productService.save(product);

		return "redirect:/";
	}

	@GetMapping("/products/{id}/edit")
	public String edit(@PathVariable Integer id, Model model, Authentication authentication) {
		Product product = productService.findById(id);

		ProductForm form = new ProductForm();
		form.setName(product.getName());
		form.setPrice(product.getPrice());

		model.addAttribute("productId", id);
		model.addAttribute("productForm", form);
		model.addAttribute("loginUserName", authentication.getName());
		model.addAttribute("isAdmin", hasAdminRole(authentication));

		return "edit";
	}

	@PostMapping("/products/{id}/update")
	public String update(@PathVariable Integer id,
			@Valid @ModelAttribute("productForm") ProductForm form,
			BindingResult bindingResult,
			Model model,
			Authentication authentication) {

		if (bindingResult.hasErrors()) {
			model.addAttribute("productId", id);
			model.addAttribute("loginUserName", authentication.getName());
			model.addAttribute("isAdmin", hasAdminRole(authentication));
			return "edit";
		}

		Product product = productService.findById(id);
		product.setName(form.getName());
		product.setPrice(form.getPrice());
		productService.save(product);

		return "redirect:/";
	}

	@PostMapping("/delete")
	public String delete(@RequestParam Integer id) {
		productService.deleteById(id);
		return "redirect:/";
	}

	private boolean hasAdminRole(Authentication authentication) {
		if (authentication == null) return false;
		return authentication.getAuthorities().stream()
				.anyMatch(a -> "ROLE_ADMIN".equals(a.getAuthority()) || "ADMIN".equals(a.getAuthority()));
	}
}
