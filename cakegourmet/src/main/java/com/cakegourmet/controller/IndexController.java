package com.cakegourmet.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.cakegourmet.model.Usuario;
import com.cakegourmet.repository.UsuarioRepository;

@Controller
@RequestMapping("/cakegourmet")
public class IndexController {
	
	@Autowired
	private UsuarioRepository ur;
	
	@RequestMapping(value = "/home", method = RequestMethod.GET)
	public String showIndex() {		
		return "home";		
	}
	
	@RequestMapping(value = "/login", method = RequestMethod.POST)
	public UserDetails login(String login) throws UsernameNotFoundException {
		
		Usuario usuario = ur.findByLogin(login);
		
		if(usuario == null){
			throw new UsernameNotFoundException("Usuario não encontrado!");
		}
		return new User(usuario.getUsername(), usuario.getPassword(), true, true, true, true, usuario.getAuthorities());
	}
}
