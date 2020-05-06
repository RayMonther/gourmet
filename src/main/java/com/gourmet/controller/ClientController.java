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
import com.gourmet.models.Client;

@Controller
@RequestMapping("/gourmet")
public class ClientController {
	
	@RequestMapping(value = "/novo-cliente", method = RequestMethod.GET)
	public ModelAndView newClient() {		
		
	    ModelAndView modelAndView = new ModelAndView("form-cliente");
	    Client client = new Client();
	    modelAndView.addObject("client", client);
	    
	    return modelAndView;
	}
	
	
	@RequestMapping(value = "/edit-cliente", method = RequestMethod.GET)
	public ModelAndView showClient(String cpf) {
		
		ModelAndView modelAndView = new ModelAndView("form-cliente");
		
		if (cpf!=null){
		    RestTemplate restTemplate = new RestTemplate();
		    Client client = restTemplate.getForObject("http://localhost:8080/clients/" + cpf, Client.class);
		    modelAndView.addObject("client", client);
		}
	    return modelAndView;
	}
	
	@RequestMapping(value = "/edit-cliente", method = RequestMethod.POST)
	public ModelAndView formshowClient(Client client) {
		
		ModelAndView modelAndView = new ModelAndView("form-cliente");
		
		try {
			String       postUrl       = "http://localhost:8080/clients/";// put in your url
			Gson         gson          = new Gson();
			HttpClient   httpClient    = HttpClientBuilder.create().build();
			HttpPost     post          = new HttpPost(postUrl);
			StringEntity postingString;
			postingString = new StringEntity(gson.toJson(client));
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
	
	@RequestMapping(value = "/clientes", method = RequestMethod.GET)
	public ModelAndView showClients() {		
		ModelAndView modelAndView = new ModelAndView("lista-cliente");
	    
	    RestTemplate restTemplate = new RestTemplate();
	    ResponseEntity<List<Client>> rateResponse =
	            restTemplate.exchange("http://localhost:8080/clients/",
	                        HttpMethod.GET, null, new ParameterizedTypeReference<List<Client>>() {
	                });
	    List<Client> listClient = rateResponse.getBody();
	    modelAndView.addObject("listClient", listClient);
	    
	    return modelAndView;
	}
	
	
	@RequestMapping(value = "/new-cliente", method = RequestMethod.POST)
	public ModelAndView newClient(Client client) {		
		
		ModelAndView modelAndView = new ModelAndView("form-cliente");
		
		
		try {
			String       postUrl       = "http://localhost:8080/clients/";// put in your url
			Gson         gson          = new Gson();
			HttpClient   httpClient    = HttpClientBuilder.create().build();
			HttpPost     post          = new HttpPost(postUrl);
			StringEntity postingString;
			postingString = new StringEntity(gson.toJson(client));
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
	
	@RequestMapping(value="/new-cliente", method=RequestMethod.GET)
	public ModelAndView formNewClient() {
		ModelAndView modelAndView = new ModelAndView("form-cliente");
		Client client = new Client();
		modelAndView.addObject("client", client);
		
		return modelAndView;
	}
}
