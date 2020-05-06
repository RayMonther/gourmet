package com.gourmet.controller;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClientBuilder;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.ModelAndView;

import com.google.gson.Gson;
import com.gourmet.models.Product;

@Controller
@RequestMapping("/gourmet")
public class ProductController {
	
	@RequestMapping(value = "/produto", method = RequestMethod.GET)
	public ModelAndView listaProdutos(){
		
	    ModelAndView modelAndView = new ModelAndView("produto");
	    
	    RestTemplate restTemplate = new RestTemplate();
	    ResponseEntity<List<Product>> rateResponse =
	            restTemplate.exchange("http://localhost:8080/products/",
	                        HttpMethod.GET, null, new ParameterizedTypeReference<List<Product>>() {
	                });
	    List<Product> listProduct = rateResponse.getBody();
	    modelAndView.addObject("listProduct", listProduct);
	    
	    return modelAndView;
	}
	
	@RequestMapping(value = "/edit-product", method = RequestMethod.POST)
	public ModelAndView formshowClient(Product product) {
		
		ModelAndView modelAndView = new ModelAndView("form-produto");
		
		try {
			String       postUrl       = "http://localhost:8080/products/";// put in your url
			Gson         gson          = new Gson();
			HttpClient   httpClient    = HttpClientBuilder.create().build();
			HttpPost     post          = new HttpPost(postUrl);
			StringEntity postingString;
			postingString = new StringEntity(gson.toJson(product));
			post.setEntity(postingString);
			post.setHeader("Content-type", "application/json");
			try {
				HttpResponse  response = httpClient.execute(post);
			} catch (ClientProtocolException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}//gson.tojson() converts your pojo to json
		
	    return modelAndView;
	}
	
	@RequestMapping(value = "/edit-product", method = RequestMethod.GET)
	public ModelAndView showProduct(String produto) {		
		
		ModelAndView modelAndView = new ModelAndView("form-produto");
		
		if (produto!=null){
		    RestTemplate restTemplate = new RestTemplate();
		    Product product = restTemplate.getForObject("http://localhost:8080/products/" + produto, Product.class);
		    modelAndView.addObject("product", product);
		}
	    return modelAndView;
	}
	
	@RequestMapping(value = "/new-product", method = RequestMethod.POST)
	public ModelAndView newProduct(Product product) {		
		
		ModelAndView modelAndView = new ModelAndView("form-produto");
		
		try {
			String       postUrl       = "http://localhost:8080/products/";// put in your url
			Gson         gson          = new Gson();
			HttpClient   httpClient    = HttpClientBuilder.create().build();
			HttpPost     post          = new HttpPost(postUrl);
			StringEntity postingString;
			postingString = new StringEntity(gson.toJson(product));
			post.setEntity(postingString);
			post.setHeader("Content-type", "application/json");
			try {
				HttpResponse  response = httpClient.execute(post);
			} catch (ClientProtocolException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}//gson.tojson() converts your pojo to json
		
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
