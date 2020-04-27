package com.gourmet.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.ModelAndView;

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
		cpf = "123456";
		ModelAndView modelAndView = new ModelAndView("form-cliente");
		
		if (cpf!=null){
		    RestTemplate restTemplate = new RestTemplate();
		    Client client = restTemplate.getForObject("http://localhost:8080/users/" + cpf, Client.class);
		    modelAndView.addObject("client", client);
		}
	    return modelAndView;
	}
	
	
	@RequestMapping(value = "/new-cliente", method = RequestMethod.POST)
	public ModelAndView newClient(Client client) {		
		
		ModelAndView modelAndView = new ModelAndView("form-cliente");
		
		if (client!=null){
		    RestTemplate restTemplate = new RestTemplate();
		    restTemplate.postForObject("http://localhost:8080/users/", client, Client.class, true);
		    modelAndView.addObject("client", client);
		}
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
