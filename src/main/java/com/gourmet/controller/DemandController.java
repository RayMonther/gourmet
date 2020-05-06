package com.gourmet.controller;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
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
import com.gourmet.models.Client;
import com.gourmet.models.Product;

@Controller
@RequestMapping("/gourmet")
public class DemandController {
	
	
	@RequestMapping(value = "/products", method = RequestMethod.GET)
	public ModelAndView listaProdutosCliente(){
		
	    ModelAndView modelAndView = new ModelAndView("produto-cliente");
	    
	    RestTemplate restTemplate = new RestTemplate();
	    ResponseEntity<List<Product>> rateResponse =
	            restTemplate.exchange("http://localhost:8080/products/",
	                        HttpMethod.GET, null, new ParameterizedTypeReference<List<Product>>() {
	                });
	    List<Product> listProduct = rateResponse.getBody();
	    modelAndView.addObject("listProduct", listProduct);
	    
	    return modelAndView;
	}
	
	@RequestMapping(value = "/products", method = RequestMethod.POST)
	public ModelAndView pedido(Product prod, Client cli) {		
		
		ModelAndView modelAndView = new ModelAndView("pedido");
		
		RestTemplate restTemplateCli = new RestTemplate();
	    Client client = restTemplateCli.getForObject("http://localhost:8080/clients/" + cli.getCpf(), Client.class);
	    
	    RestTemplate restTemplateProd = new RestTemplate();
	    Product product = restTemplateProd.getForObject("http://localhost:8080/products/" + prod.getName(), Product.class);
		
	    ArrayList al=new ArrayList();
	    al.add(client);
	    al.add(product);
	    
	    try {
			String       postUrl       = "http://localhost:8080/demands/";// put in your url
			Gson         gson          = new Gson();
			HttpClient   httpClient    = HttpClientBuilder.create().build();
			HttpPost     post          = new HttpPost(postUrl);
			StringEntity postingString;
			postingString = new StringEntity(gson.toJson(al));
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
}
