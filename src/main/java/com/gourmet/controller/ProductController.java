package com.gourmet.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.ModelAndView;

import com.gourmet.models.Product;

@Controller
@RequestMapping("/gourmet")
public class ProductController {
	@RequestMapping(value = "/products", method = RequestMethod.GET)
	public String showProducts() {		
		return "produto";		
	}
	
	@RequestMapping(value = "/produto", method = RequestMethod.GET)
	public ModelAndView listaProdutos(String nome){
		nome = "Prestigio";
	    ModelAndView modelAndView = new ModelAndView("produto");
	    
	    if (nome!=null){
	        RestTemplate restTemplate = new RestTemplate();
	        Product product = restTemplate.getForObject("http://localhost:8080/products/" + nome, Product.class);
	        modelAndView.addObject("product", product);
	    }
	    return modelAndView;
	}
	
	@RequestMapping(value = "/edit-product", method = RequestMethod.GET)
	public ModelAndView showProduct(String produto) {		
		produto = "Prestigio";
		ModelAndView modelAndView = new ModelAndView("form-produto");
		
		if (produto!=null){
		    RestTemplate restTemplate = new RestTemplate();
		    Product product = restTemplate.getForObject("http://localhost:8080/products/" + produto, Product.class);
		    modelAndView.addObject("product", product);
		}
	    return modelAndView;
	}
	
	@RequestMapping(value = "/new-produto", method = RequestMethod.POST)
	public ModelAndView newProduct(Product product) {		
		
		ModelAndView modelAndView = new ModelAndView("form-produto");
		
		if (product!=null){
		    RestTemplate restTemplate = new RestTemplate();
		    restTemplate.postForObject("http://localhost:8080/products/", product, Product.class, true);
		    modelAndView.addObject("product", product);
		}
	    return modelAndView;
	}
	
	@RequestMapping(value="/new-product", method=RequestMethod.GET)
	public ModelAndView formNewProduct() {
		ModelAndView modelAndView = new ModelAndView("form-produto");
		Product product = new Product();
		modelAndView.addObject("product", product);
		
		return modelAndView;
	}
}
