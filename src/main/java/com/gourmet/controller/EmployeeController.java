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
import com.gourmet.models.Employee;

@Controller
@RequestMapping("/gourmet")
public class EmployeeController {
	@RequestMapping(value = "/novo-funcionario", method = RequestMethod.GET)
	public ModelAndView newEmployee() {		
		
	    ModelAndView modelAndView = new ModelAndView("form-funcionario");
	    Employee employee = new Employee();
	    modelAndView.addObject("employee", employee);
	    
	    return modelAndView;
	}
	
	
	@RequestMapping(value = "/edit-funcionario", method = RequestMethod.GET)
	public ModelAndView showEmployee(Long id) {
		
		ModelAndView modelAndView = new ModelAndView("form-funcionario");
		
		if (id!=null){
		    RestTemplate restTemplate = new RestTemplate();
		    Employee employee = restTemplate.getForObject("http://localhost:8080/employee/" + id, Employee.class);
		    modelAndView.addObject("employee", employee);
		}
	    return modelAndView;
	}
	
	@RequestMapping(value = "/edit-funcionario", method = RequestMethod.POST)
	public ModelAndView formshowEmployee(Employee employee) {
		
		ModelAndView modelAndView = new ModelAndView("form-funcionario");
		
		try {
			String       postUrl       = "http://localhost:8080/employee/";// put in your url
			Gson         gson          = new Gson();
			HttpClient   httpClient    = HttpClientBuilder.create().build();
			HttpPost     post          = new HttpPost(postUrl);
			StringEntity postingString;
			postingString = new StringEntity(gson.toJson(employee));
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
	
	@RequestMapping(value = "/funcionarios", method = RequestMethod.GET)
	public ModelAndView showEmployees() {		
		ModelAndView modelAndView = new ModelAndView("funcionario");
	    
	    RestTemplate restTemplate = new RestTemplate();
	    ResponseEntity<List<Employee>> rateResponse =
	            restTemplate.exchange("http://localhost:8080/employee/",
	                        HttpMethod.GET, null, new ParameterizedTypeReference<List<Employee>>() {
	                });
	    List<Employee> employee = rateResponse.getBody();
	    modelAndView.addObject("employee", employee);
	    
	    return modelAndView;
	}
	
	
	@RequestMapping(value = "/new-funcionario", method = RequestMethod.POST)
	public ModelAndView newEmployee(Employee employee) {		
		
		ModelAndView modelAndView = new ModelAndView("form-funcionario");
		
		
		try {
			String       postUrl       = "http://localhost:8080/employee/";// put in your url
			Gson         gson          = new Gson();
			HttpClient   httpClient    = HttpClientBuilder.create().build();
			HttpPost     post          = new HttpPost(postUrl);
			StringEntity postingString;
			postingString = new StringEntity(gson.toJson(employee));
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
	
	@RequestMapping(value="/new-funcionario", method=RequestMethod.GET)
	public ModelAndView formNewEmployee() {
		ModelAndView modelAndView = new ModelAndView("form-funcionario");
		Employee employee = new Employee();
		modelAndView.addObject("employee", employee);
		
		return modelAndView;
	}
}
